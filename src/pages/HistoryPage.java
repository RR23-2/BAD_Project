package pages;

import database.Database;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import objects.Transaction;
import objects.TransactionDetail;

public class HistoryPage {
	@SuppressWarnings("unchecked")
	public static VBox historyPage() {
		// components
		VBox historyPage = new VBox();
		HBox mainPage = new HBox();
		VBox leftPage = new VBox();
		VBox rightPage = new VBox();
		Label myTransactionLbl = new Label("My Transaction");
		Label transactionDetailLbl = new Label("Transaction Detail");
		TableView<Transaction> transactionTableView = new TableView<>();
		TableView<TransactionDetail> transactionDetailTableView = new TableView<>();
		Label totalPriceLbl = new Label("Total Price: ");
		
		// transaction table
		ObservableList<Transaction> transactionTable = Database.fetchUserTransaction();
		TableColumn<Transaction, String> idColumn = new TableColumn<>("ID");
		TableColumn<Transaction, String> emailColumn = new TableColumn<>("Email");
		TableColumn<Transaction, String> dateColumn = new TableColumn<>("Date");
				
		transactionTableView.setItems(transactionTable);
		idColumn.setCellValueFactory(new PropertyValueFactory<>("transactionID"));
		emailColumn.setCellValueFactory(new PropertyValueFactory<>("userEmail"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));
		
		transactionTableView.getColumns().addAll(idColumn, emailColumn, dateColumn);
		transactionTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		transactionTableView.setMinWidth(300);
		
		// transaction detail
		TableColumn<TransactionDetail, String> detailIdColumn = new TableColumn<>("ID");
		TableColumn<TransactionDetail, String> productNameColumn = new TableColumn<>("Product");
		TableColumn<TransactionDetail, Integer> productPriceColumn = new TableColumn<>("Price");
		TableColumn<TransactionDetail, Integer> quantityColumn = new TableColumn<>("Quantity");
		TableColumn<TransactionDetail, Integer> totalPriceColumn = new TableColumn<>("Total Price");
		
		detailIdColumn.setCellValueFactory(new PropertyValueFactory<>("transactionID"));
		productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
		productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
		quantityColumn.setCellValueFactory(new PropertyValueFactory<>("productQuantity"));
		totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
		
		transactionDetailTableView.getColumns().addAll(detailIdColumn, productNameColumn, productPriceColumn, quantityColumn, totalPriceColumn);
		transactionDetailTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		transactionDetailTableView.setMinWidth(5);
		
		// left page
		leftPage.getChildren().addAll(myTransactionLbl, transactionTableView);
		leftPage.setSpacing(5);
		
		// right page
		rightPage.getChildren().addAll(transactionDetailLbl, transactionDetailTableView, totalPriceLbl);
		rightPage.setSpacing(5);
		
		// main page
		mainPage.getChildren().addAll(leftPage, rightPage);
		mainPage.setSpacing(10);
		mainPage.setAlignment(Pos.CENTER);
		
		// history page
		historyPage.getChildren().add(mainPage);
		historyPage.setAlignment(Pos.CENTER);
		
		// view detail
		transactionTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			ObservableList<TransactionDetail> viewedDetail = Database.fetchTransactionDetail(newValue.transactionID);
			int totalPrice = viewedDetail.stream().mapToInt(TransactionDetail::getTotalPrice).sum();
			transactionDetailTableView.setItems(viewedDetail);
			totalPriceLbl.setText("Total Price: " + totalPrice);
		});
		
		return historyPage;
	}
}
