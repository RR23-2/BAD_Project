package pages;

import database.Database;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import main.Main;
import objects.Product;

public class HomePage {
	
	@SuppressWarnings("unchecked")
	public static VBox homePage() {
		// components
		VBox homePage = new VBox();
		HBox mainPage = new HBox();
		VBox leftPage = new VBox();
		VBox rightPage = new VBox();
		Label productListLbl = new Label("Product List");
		productListLbl.setFont(new Font(18));
		Label productNameLbl = new Label("Product Name : ");
		Label productBrandLbl = new Label("Product Brand : ");
		Label productPriceLbl = new Label("Price : ");
		Label productTotalPriceLbl = new Label("Total Price : ");
		TableView<Product> productTableView = new TableView<>();
		Spinner<Integer> quantitySpinner = new Spinner<>(0, 0, 0);
		quantitySpinner.setEditable(true);
		Button addToCartBtn = new Button("Add to Cart");
		
		// table view
		ObservableList<Product> productTable = Database.fetchStockedProduct();
		TableColumn<Product, String> nameColumn = new TableColumn<>("Name");
		TableColumn<Product, String> brandColumn = new TableColumn<>("Brand");
		TableColumn<Product, Integer> stockColumn = new TableColumn<>("Stock");
		TableColumn<Product, Integer> priceColumn = new TableColumn<>("Price");
		
		productTableView.setItems(productTable);
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
		brandColumn.setCellValueFactory(new PropertyValueFactory<>("productBrand"));
		stockColumn.setCellValueFactory(new PropertyValueFactory<>("productStock"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
		
		productTableView.getColumns().addAll(nameColumn, brandColumn, stockColumn, priceColumn);
		productTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		// left page
		leftPage.getChildren().addAll(productTableView);
		leftPage.setSpacing(5);
		
		// right page
		rightPage.getChildren().addAll(productNameLbl, productBrandLbl, productPriceLbl, quantitySpinner, productTotalPriceLbl, addToCartBtn);
		rightPage.setSpacing(5);
		
		// main page
		mainPage.getChildren().addAll(leftPage, rightPage);
		mainPage.setSpacing(5);
		
		// home page
		homePage.getChildren().addAll(productListLbl, mainPage);
		homePage.setSpacing(5);
		homePage.setMaxSize(500, 500);
		
		// show product detail
		productTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showProductDetail(newValue, productNameLbl, productBrandLbl, productPriceLbl, quantitySpinner, productTotalPriceLbl));
		
		// add to cart
		addToCartBtn.setOnMouseClicked(e -> addToCart(productTableView.getSelectionModel().getSelectedItem(), quantitySpinner.getValue()));
		
		return homePage;
	}
	
	private static void addToCart(Product product, int quantity) {
		Alert warningAlert = new Alert(AlertType.WARNING);
		warningAlert.setTitle("Warning");
		warningAlert.setHeaderText("Warning");
		if(product == null) {
			warningAlert.setContentText("Please Choose 1 Item");
			warningAlert.show();
		}
		else if(quantity <= 0) {
			warningAlert.setContentText("Please add at least 1 quantity");
			warningAlert.show();
		}
		else {
			Database.addToCart(Main.loggedInUser.userID, product.productID, quantity);
			Main.home();
		}
	}
	
	private static void showProductDetail(Product product, Label productNameLbl, Label productBrandLbl, Label productPriceLbl, Spinner<Integer> quantitySpinner, Label productTotalPriceLbl) {
		productNameLbl.setText("Product Name : " + product.productName);
		productBrandLbl.setText("Product Brand : " + product.productBrand);
		productPriceLbl.setText("Price : " + product.productPrice);
		quantitySpinner.setValueFactory(new IntegerSpinnerValueFactory(0, product.productStock));
		quantitySpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
			productTotalPriceLbl.setText("Total Price : " + newValue * product.productPrice);
		});
	}
	
}
