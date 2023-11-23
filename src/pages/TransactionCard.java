package pages;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import database.Database;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import jfxtras.labs.scene.control.window.Window;
import main.Main;
import objects.CartProduct;

public class TransactionCard {
	public static Window transactionCard() {
		// components
		Window transactionCard = new Window();
		VBox mainPage = new VBox();
		Label listLbl = new Label("List");
		VBox productListBox = new VBox();
		Label courierLbl = new Label("Courier");
		ArrayList<CartProduct> cartTable = Database.fetchCheckout();
		int totalPrice = cartTable.stream().mapToInt(CartProduct::getTotal).sum();
		Label productTotalPriceLbl = new Label("Total Price :" + totalPrice);
		ComboBox<String> courierCBox = new ComboBox<>();
		CheckBox insuranceCheck = new CheckBox("Use Insurance");
		Button checkoutBtn = new Button("Checkout");
		
		// combo box
		courierCBox.getItems().addAll("J&E", "Nanji Express", "Gejok", "JET");
		courierCBox.setValue("J&E");
		
		// product list box
		for(CartProduct cp : cartTable) {
			Label productLbl = new Label(cp.productName + " : " + (cp.quantity * cp.productPrice));
			productListBox.getChildren().add(productLbl);
		}
		productListBox.setAlignment(Pos.CENTER);
		
		// main page
		mainPage.getChildren().addAll(listLbl, productListBox, courierLbl, courierCBox, insuranceCheck, productTotalPriceLbl, checkoutBtn);
		mainPage.setSpacing(10);
		mainPage.setMaxSize(500, 500);
		mainPage.setAlignment(Pos.CENTER);
		
		// window
		transactionCard.setTitle("Transaction Card");
		transactionCard.getContentPane().getChildren().add(mainPage);
		
		// insurance
		insuranceCheck.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue) {
				productTotalPriceLbl.setText("Total Price :" + (totalPrice + 90000));
			}
			else {
				productTotalPriceLbl.setText("Total Price :" + totalPrice);
			}
		});
		
		// check out
		checkoutBtn.setOnMouseClicked(e -> checkout(insuranceCheck.selectedProperty().getValue(), courierCBox.getValue()));
		
		return transactionCard;
	}
	
	private static void checkout(boolean insurance, String courier) {
		Alert confirmAlert = new Alert(AlertType.CONFIRMATION);
		confirmAlert.setTitle("Confirmation");
		confirmAlert.setHeaderText("Are you sure you want to checkout all the item?");
		confirmAlert.setContentText("Need Confirmation");
		Optional<ButtonType> options = confirmAlert.showAndWait();
		if(options.get() == ButtonType.OK) {
			LocalDate curDate = LocalDate.now();
			Database.checkout(curDate, insurance, courier);
		}
		else {
			Main.doneTransaction();
		}
	}
}
