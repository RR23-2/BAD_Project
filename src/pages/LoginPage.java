package pages;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import main.Main;
import objects.User;
import java.util.ArrayList;

import database.Database;

public class LoginPage {
	
	public static VBox loginPage() {
		// login form components
		VBox loginForm = new VBox();
		HBox loginLblBox = new HBox();
		Label loginLbl = new Label("Login");
		Label emailLbl = new Label("Email");
		Label passwordLbl = new Label("Password");
		TextField emailField = new TextField();
		PasswordField passwordField = new PasswordField();
		Button loginBtn = new Button("Login");
		
		// set loginLblBox
		loginLbl.setFont(new Font(18));
		loginLblBox.getChildren().add(loginLbl);
		loginLblBox.setAlignment(Pos.CENTER);
		
		// create login form
		loginForm.setSpacing(5);
		loginForm.getChildren().addAll(loginLblBox, emailLbl, emailField, passwordLbl, passwordField, loginBtn);
		loginForm.setMaxSize(300, 300);
		
		
		// validation
		loginBtn.setOnMouseClicked(e -> validateLogin(emailField.getText(), passwordField.getText()));
		
		return loginForm;
	}
	
	private static void validateLogin(String email, String password) {
		Alert warningAlert = new Alert(AlertType.WARNING);
		warningAlert.setTitle("Warning");
		warningAlert.setHeaderText("Warning");
		
		// if any field is empty
		if(email.isEmpty() || password.isEmpty()) {
			warningAlert.setContentText("Email and Password must be filled!");
			warningAlert.show();
		}
		// check if user is in db or not
		else {
			ArrayList<User> userTable = new ArrayList<>();
			userTable = Database.fetchUser();
			for (User u : userTable) {
				if(u.userEmail.equals(email) && u.userPassword.equals(password)) {
					Main.loggedInUser = u;
					if(u.userRole.equals("User")) Main.home();
					else Main.manageProduct();
					return;
				}
			}
			warningAlert.setContentText("Wrong Email or Password!");
			warningAlert.show();
		}
		return;
	}
}
