package pages;

import database.Database;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import main.Main;
import objects.CartProduct;

public class CartPage {
	
	@SuppressWarnings("unchecked")
	public static VBox cartPage() {
		// components
		VBox cartPage = new VBox();
		HBox midPage = new HBox();
		VBox rightPage = new VBox();
		VBox leftPage = new VBox();
		VBox bottomPage = new VBox();
		Label yourCartListLbl = new Label("Your Cart List");
		yourCartListLbl.setFont(new Font(18));
		Label productNameLbl = new Label("Name : ");
		Label productBrandLbl = new Label("Brand : ");
		Label productPriceLbl = new Label("Price : ");
		ObservableList<CartProduct> cartTable = Database.fetchCart();
		int totalPrice = cartTable.stream().mapToInt(CartProduct::getTotal).sum();
		Label productTotalPriceLbl = new Label("Total Price :" + totalPrice);
		Button checkOutBtn = new Button("Checkout");
		Button deleteProductBtn = new Button("Delete Product");
		TableView<CartProduct> cartTableView = new TableView<>();
		
		// cart table view
		TableColumn<CartProduct, String> nameColumn = new TableColumn<>("Name");
		TableColumn<CartProduct, String> brandColumn = new TableColumn<>("Brand");
		TableColumn<CartProduct, Integer> priceColumn = new TableColumn<>("Price");
		TableColumn<CartProduct, Integer> quantityColumn = new TableColumn<>("Quantity");
		TableColumn<CartProduct, Integer> totalColumn = new TableColumn<>("Total");
		
		cartTableView.setItems(cartTable);
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
		brandColumn.setCellValueFactory(new PropertyValueFactory<>("productBrand"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
		quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
		
		cartTableView.getColumns().addAll(nameColumn, brandColumn, priceColumn, quantityColumn, totalColumn);
		cartTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		// mid page
		midPage.getChildren().addAll(leftPage, rightPage);
		midPage.setSpacing(5);
		
		// right page
		rightPage.getChildren().addAll(productNameLbl, productBrandLbl, productPriceLbl, productTotalPriceLbl);
		rightPage.setSpacing(5);
		
		// left page
		leftPage.getChildren().addAll(cartTableView);
		
		// bottom page
		bottomPage.getChildren().addAll(checkOutBtn, deleteProductBtn);
		checkOutBtn.setMinWidth(400);
		deleteProductBtn.setMinWidth(400);	
		bottomPage.setSpacing(5);
		
		// cart page
		cartPage.getChildren().addAll(yourCartListLbl, midPage, bottomPage);
		cartPage.setMaxSize(600, 600);
		cartPage.setSpacing(5);
		
		// show product detail
		cartTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showProductDetail(newValue, productNameLbl, productBrandLbl, productPriceLbl, productTotalPriceLbl));
		
		// delete product 
		deleteProductBtn.setOnMouseClicked(e -> deleteProduct(cartTableView.getSelectionModel().getSelectedItem()));
		
		// checkout
		checkOutBtn.setOnMouseClicked(e -> checkout());
		
		return cartPage;
	}
	
	private static void checkout() {
		ObservableList<CartProduct> cartTable = Database.fetchCart();
		Alert warningAlert = new Alert(AlertType.WARNING);
		warningAlert.setTitle("Warning");
		warningAlert.setHeaderText("Warning");
		if(cartTable.size() <= 0) {
			warningAlert.setContentText("Please insert item to your cart");
			warningAlert.show();
		}
		// else direct to checkout
	}
	
	private static void deleteProduct(CartProduct cartProduct) {
		Alert warningAlert = new Alert(AlertType.WARNING);
		warningAlert.setTitle("Warning");
		warningAlert.setHeaderText("Warning");
		if(cartProduct == null) {
			warningAlert.setContentText("Please Select product to Delete");
			warningAlert.show();
		}
		else {
			Database.removeFromCart(cartProduct.productID, cartProduct.quantity);
			Main.cart();
		}
	}
	
	private static void showProductDetail(CartProduct product, Label productNameLbl, Label productBrandLbl, Label productPriceLbl, Label productTotalPriceLbl) {
		productNameLbl.setText("Product Name : " + product.productName);
		productBrandLbl.setText("Product Brand : " + product.productBrand);
		productPriceLbl.setText("Price : " + product.productPrice);
	}
	
}
