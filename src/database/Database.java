package database;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Main;
import objects.CartProduct;
import objects.Product;
import objects.Transaction;
import objects.TransactionDetail;
import objects.User;
import java.sql.*;
import java.time.LocalDate;

public class Database {
	
	// fetch all user
	public static ArrayList<User> fetchUser() {
		ArrayList<User> userTable = new ArrayList<>();
		
		Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/CLminton", "root", "");
 
            Statement statement;
            statement = connection.createStatement();
            ResultSet resultSet;
            resultSet = statement.executeQuery("select * from msuser");
            
            String userID, userEmail, userPassword, userGender, userNationality, userRole;
            int userAge;
            while (resultSet.next()) {
                userID = resultSet.getString("UserID");
                userEmail = resultSet.getString("UserEmail");
                userPassword = resultSet.getString("UserPassword");
                userAge = resultSet.getInt("UserAge");
                userGender = resultSet.getString("UserGender");
                userNationality = resultSet.getString("UserNationality");
                userRole = resultSet.getString("UserRole");
                userTable.add(new User(userID, userEmail, userPassword, userAge, userGender, userNationality, userRole));
            }
            resultSet.close();
            statement.close();
            connection.close();
        }
        catch (Exception exception) {
            System.out.println(exception);
        }
		
		return userTable;
	}

	// insert user
	public static void insertUser(String userID, String userEmail, String userPassword, int userAge, String userGender, String userNationality, String userRole) {
		Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/CLminton", "root", "");
 
            Statement statement;
            statement = connection.createStatement();

            statement.executeUpdate(String.format("insert into msuser values('%s', '%s', '%s', %d, '%s', '%s', '%s')", userID, userEmail, userPassword, userAge, userGender, userNationality, userRole));
            
            statement.close();
            connection.close();
        }
        catch (Exception exception) {
            System.out.println(exception);
        }
	}
	
	// fetch stocked product (for user home page)
	public static ObservableList<Product> fetchStockedProduct() {
		ObservableList<Product> stockedProductTable = 	FXCollections.observableArrayList();

		
		Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/CLminton", "root", "");
 
            Statement statement;
            statement = connection.createStatement();
            ResultSet resultSet;
            resultSet = statement.executeQuery("select * from msproduct where ProductStock > 0");
            
            String productID, productName, productBrand;
        	int productPrice, productStock;
            while (resultSet.next()) {
            	productID = resultSet.getString("ProductID");
            	productName = resultSet.getString("ProductName");
            	productBrand = resultSet.getString("ProductMerk");
            	productPrice = resultSet.getInt("ProductPrice");
            	productStock = resultSet.getInt("ProductStock");
                stockedProductTable.add(new Product(productID, productName, productBrand, productPrice, productStock));
            }
            resultSet.close();
            statement.close();
            connection.close();
        }
        catch (Exception exception) {
            System.out.println(exception);
        }
		
		return stockedProductTable;
	}
	
	// add to cart
	public static void addToCart(String userID, String productID, int quantity) {
		Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/CLminton", "root", "");
 
            Statement statement;
            statement = connection.createStatement();
            
            ResultSet resultSet;
            resultSet = statement.executeQuery(String.format("select * from carttable where UserID like '%s' and ProductID like '%s'", userID, productID));
            
            if(resultSet.next()) {
            	statement.executeUpdate(String.format("update carttable set Quantity = Quantity + %d where UserID like '%s' and ProductID like '%s'", quantity, userID, productID));
            }
            else statement.executeUpdate(String.format("insert into carttable values('%s', '%s', %d)", userID, productID, quantity));
            
            statement.executeUpdate(String.format("update msproduct set ProductStock = ProductStock - %d where ProductID like '%s'", quantity, productID));
            
            statement.close();
            connection.close();
        }
        catch (Exception exception) {
            System.out.println(exception);
        }
	}

	// fetch current user's cart
	public static ObservableList<CartProduct> fetchCart(){

		
		ObservableList<CartProduct> cartTable = FXCollections.observableArrayList();
		
		Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/CLminton", "root", "");
 
            Statement statement;
            statement = connection.createStatement();
            ResultSet resultSet;
            resultSet = statement.executeQuery(String.format("select mp.ProductID, ProductName, ProductMerk, ProductPrice, Quantity from msproduct mp join carttable ct on mp.ProductID = ct.ProductID where ct.UserID like '%s'", Main.loggedInUser.userID));
            
            String productID, productName, productBrand;
        	int productPrice, quantity;
            while (resultSet.next()) {
            	productID = resultSet.getString("ProductID");
            	productName = resultSet.getString("ProductName");
            	productBrand = resultSet.getString("ProductMerk");
            	productPrice = resultSet.getInt("ProductPrice");
            	quantity = resultSet.getInt("Quantity");
                cartTable.add(new CartProduct(productID, productName, productBrand, productPrice, quantity));
            }
            resultSet.close();
            statement.close();
            connection.close();
        }
        catch (Exception exception) {
            System.out.println(exception);
        }
		
		return cartTable;
	}
	
	// remove from cart
	public static void removeFromCart(String productID, int quantity) {
		Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/CLminton", "root", "");
 
            Statement statement;
            statement = connection.createStatement();
             
            statement.executeUpdate(String.format("delete from carttable where UserID like '%s' and ProductID like '%s'", Main.loggedInUser.userID, productID));
            statement.executeUpdate(String.format("update msproduct set ProductStock = ProductStock + %d where ProductID like '%s'", quantity, productID));
            
            statement.close();
            connection.close();
        }
        catch (Exception exception) {
            System.out.println(exception);
        }
	}
	
	// fetch all cart for check out
	public static ArrayList<CartProduct> fetchCheckout(){
		ArrayList<CartProduct> cartTable = new ArrayList<>();
		
		Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/CLminton", "root", "");
 
            Statement statement;
            statement = connection.createStatement();
            ResultSet resultSet;
            resultSet = statement.executeQuery(String.format("select mp.ProductID, ProductName, ProductMerk, ProductPrice, Quantity from msproduct mp join carttable ct on mp.ProductID = ct.ProductID where ct.UserID like '%s'", Main.loggedInUser.userID));
            
            String productID, productName, productBrand;
        	int productPrice, quantity;
            while (resultSet.next()) {
            	productID = resultSet.getString("ProductID");
            	productName = resultSet.getString("ProductName");
            	productBrand = resultSet.getString("ProductMerk");
            	productPrice = resultSet.getInt("ProductPrice");
            	quantity = resultSet.getInt("Quantity");
                cartTable.add(new CartProduct(productID, productName, productBrand, productPrice, quantity));
            }
            resultSet.close();
            statement.close();
            connection.close();
        }
        catch (Exception exception) {
            System.out.println(exception);
        }
		
		return cartTable;
	}
	
	// checkout
	public static void checkout(LocalDate curDate, boolean insurance, String courier) {
		Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/CLminton", "root", "");
 
            Statement statement;
            statement = connection.createStatement();
            ResultSet resultSet;
            resultSet = statement.executeQuery("select count(*) as 'TrCount' from transactionheader");
            int trCount = 0;
            while(resultSet.next()) {
            	trCount = resultSet.getInt("TrCount");
            }
            String transactionID = "TH" + String.format("%03d", (trCount + 1));
            
            statement.executeUpdate(String.format("insert into transactionheader values('%s', '%s', '%s', %s, '%s')", transactionID, Main.loggedInUser.userID, curDate, String.valueOf(insurance), courier));
            
            ArrayList<CartProduct> checkedOut = fetchCheckout();
            
            for(CartProduct cp : checkedOut) {
            	statement.executeUpdate(String.format("insert into transactiondetail values('%s', '%s', %d)", cp.productID, transactionID, cp.quantity));
            }
            
            statement.executeUpdate(String.format("delete from carttable where UserID like '%s'", Main.loggedInUser.userID));
            
            resultSet.close();
            statement.close();
            connection.close();
        }
        catch (Exception exception) {
            System.out.println(exception);
        }
        Main.doneTransaction();
	}

	// fetch user's transaction
	public static ObservableList<Transaction> fetchUserTransaction() {
		ObservableList<Transaction> userTransactionTable = 	FXCollections.observableArrayList();

		
		Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/CLminton", "root", "");
 
            Statement statement;
            statement = connection.createStatement();
            ResultSet resultSet;
            resultSet = statement.executeQuery(String.format("select TransactionID, UserEmail, TransactionDate from transactionheader th join msuser mu on th.UserID = mu.UserID where mu.UserID like '%s'", Main.loggedInUser.userID));
            
            String transactionID, userEmail, transactionDate;
            while (resultSet.next()) {
            	transactionID = resultSet.getString("TransactionID");
            	userEmail = resultSet.getString("UserEmail");
            	transactionDate = resultSet.getString("TransactionDate");
            	userTransactionTable.add(new Transaction(transactionID, userEmail, transactionDate));
            }
            resultSet.close();
            statement.close();
            connection.close();
        }
        catch (Exception exception) {
            System.out.println(exception);
        }
		
		return userTransactionTable;
	}

	// fetch transaction detail by id
	public static ObservableList<TransactionDetail> fetchTransactionDetail(String transactionID){
		ObservableList<TransactionDetail> transactionDetailTable = FXCollections.observableArrayList();
		
		Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/CLminton", "root", "");
 
            Statement statement;
            statement = connection.createStatement();
            ResultSet resultSet;
            resultSet = statement.executeQuery(String.format("select TransactionID, ProductName, ProductPrice, Quantity, Quantity * ProductPrice as 'TotalPrice' from transactiondetail tc join msproduct mp on tc.ProductID = mp.ProductID where TransactionID like '%s'", transactionID));
            
            String productName;
            int productPrice, productQuantity, totalPrice;
            while (resultSet.next()) {
            	productName = resultSet.getString("ProductName");
            	productPrice = resultSet.getInt("ProductPrice");
            	productQuantity = resultSet.getInt("Quantity");
            	totalPrice = resultSet.getInt("TotalPrice");
            	transactionDetailTable.add(new TransactionDetail(transactionID, productName, productPrice, productQuantity, totalPrice));
            }
            resultSet.close();
            statement.close();
            connection.close();
        }
        catch (Exception exception) {
            System.out.println(exception);
        }
		
		return transactionDetailTable;
	}
}