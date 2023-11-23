package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import objects.User;
import pages.CartPage;
import pages.GlobalMenuBar;
import pages.HistoryPage;
import pages.HomePage;
import pages.LoginPage;
import pages.RegisterPage;
import pages.TransactionCard;

public class Main extends Application{
	
	static BorderPane mainPane = new BorderPane();
	static Stage stage = new Stage();
	static Scene mainScene = new Scene(mainPane, 1000, 1000);
	static MenuBar globalMenuBar = GlobalMenuBar.globalMenuBar();
	
	// logged in user
	public static User loggedInUser = null;
	
	public void initScene() {
		// set global menubar for mainPane
		mainPane.setTop(globalMenuBar);
		login();
	}
	
	
	public static void login() {
		// direct to login page
		globalMenuBar.getMenus().get(0).setVisible(true);
		globalMenuBar.getMenus().get(1).setVisible(false);
		globalMenuBar.getMenus().get(2).setVisible(false);	
		mainPane.setCenter(LoginPage.loginPage());
		stage.setTitle("Login");
	}
	
	public static void register() {
		globalMenuBar.getMenus().get(0).setVisible(true);
		globalMenuBar.getMenus().get(1).setVisible(false);
		globalMenuBar.getMenus().get(2).setVisible(false);	
		mainPane.setCenter(RegisterPage.registerPage());
		stage.setTitle("Register");
	}
	
	public static void logout() {
		// log out
		login();
		loggedInUser = null;
	}
	
	// user
	public static void home() {
		// direct to home page
		globalMenuBar.getMenus().get(0).setVisible(false);
		globalMenuBar.getMenus().get(1).setVisible(true);
		globalMenuBar.getMenus().get(2).setVisible(false);
		mainPane.setCenter(HomePage.homePage());
		stage.setTitle("Home");
		
	}
	
	public static void cart() {
		// direct to cart page
		globalMenuBar.getMenus().get(0).setVisible(false);
		globalMenuBar.getMenus().get(1).setVisible(true);
		globalMenuBar.getMenus().get(2).setVisible(false);
		mainPane.setCenter(CartPage.cartPage());
		stage.setTitle("Cart");
	}
	
	public static void transactionCard() {
		mainPane.setTop(null);
		mainPane.setCenter(TransactionCard.transactionCard());
	}
	
	public static void doneTransaction() {
		mainPane.setTop(globalMenuBar);
		cart();
	}
	
	public static void history() {
		// direct to history page
		globalMenuBar.getMenus().get(0).setVisible(false);
		globalMenuBar.getMenus().get(1).setVisible(true);
		globalMenuBar.getMenus().get(2).setVisible(false);
		mainPane.setCenter(HistoryPage.historyPage());
		stage.setTitle("My History");
	}
	
	// admin
	public static void manageProduct() {
		// direct to manageProduct page
		globalMenuBar.getMenus().get(0).setVisible(false);
		globalMenuBar.getMenus().get(1).setVisible(false);
		globalMenuBar.getMenus().get(2).setVisible(true);
	}
	
	public static void viewHistory() {
		// direct to viewHistory page
		globalMenuBar.getMenus().get(0).setVisible(false);
		globalMenuBar.getMenus().get(1).setVisible(false);
		globalMenuBar.getMenus().get(2).setVisible(true);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage = stage;
		initScene();
		stage.setScene(mainScene);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
