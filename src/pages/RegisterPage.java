package pages;

import java.util.ArrayList;

import database.Database;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import objects.User;

public class RegisterPage {
	
	public static VBox registerPage() {
		// register form components
		VBox registerForm = new VBox();
		HBox registerLblBox = new HBox();
		Label registerLbl = new Label("Register");
		HBox mainBox = new HBox();
		VBox leftForm = new VBox();
		VBox rightForm = new VBox();
		Label emailLbl = new Label("Email");
		Label passwordLbl = new Label("Password");
		Label confirmPasswordLbl = new Label("Confirm Password");
		Label ageLbl = new Label("Age");
		Label genderLbl = new Label("Gender");
		Label nationalityLbl = new Label("Nationality");
		TextField emailField = new TextField();
		PasswordField passwordField = new PasswordField();
		PasswordField confirmPasswordField = new PasswordField();
		Spinner<Integer> ageSpinner = new Spinner<>(0, 150, 0);
		ToggleGroup genderGroup = new ToggleGroup();
		RadioButton maleButton = new RadioButton("Male");
		maleButton.setToggleGroup(genderGroup);
		RadioButton femaleButton = new RadioButton("Female");
		femaleButton.setToggleGroup(genderGroup);
		ComboBox<String> nationalityCBox = new ComboBox<>();
		Button registerButton = new Button("Register");
		
		// set nationality combo box
		nationalityCBox.getItems().addAll("Nationality", "Indonesia", "China", "Japan", "Korea", "Malaysia");
		nationalityCBox.setValue("Nationality");
		
		// set registerLblBox
		registerLbl.setFont(new Font(18));
		registerLblBox.getChildren().add(registerLbl);
		registerLblBox.setAlignment(Pos.CENTER);
		
		// set left form
		leftForm.getChildren().addAll(emailLbl, emailField, passwordLbl, passwordField, confirmPasswordLbl, confirmPasswordField, ageLbl, ageSpinner);
		ageSpinner.setEditable(true);
		leftForm.setSpacing(5);
		
		// set right form
		rightForm.getChildren().addAll(genderLbl, maleButton, femaleButton, nationalityLbl, nationalityCBox, registerButton);
		rightForm.setSpacing(5);
		
		// set mainBox
		mainBox.getChildren().addAll(leftForm, rightForm);
		mainBox.setSpacing(10);
		
		// set registerForm
		registerForm.setSpacing(10);
		registerForm.getChildren().addAll(registerLblBox, mainBox);
		registerForm.setMaxSize(300, 300);
		registerButton.setOnMouseClicked(e -> validateRegistration(emailField.getText(), passwordField.getText(), confirmPasswordField.getText(), ageSpinner.getValue(), genderGroup, nationalityCBox.getValue()));
		
		return registerForm;
	}
	
	private static void validateRegistration(String email, String password, String confirmPassword, Integer age, ToggleGroup genderGroup, String nationality) {
		Alert warningAlert = new Alert(AlertType.WARNING);
		warningAlert.setTitle("Warning");
		warningAlert.setHeaderText("Warning");
		
		
		boolean unique = true;
		ArrayList<User> userTable = Database.fetchUser();
		for(User u : userTable) {
			if(u.userEmail.equals(email)) unique = false;
		}
		
		if(!email.endsWith("@gmail.com")) {
			warningAlert.setContentText("Email must end with '@gmail.com'!");
			warningAlert.show();
		}
		else if(unique == false) {
			warningAlert.setContentText("Email has already been registered!");
			warningAlert.show();
		}
		else if(password.length() < 6) {
			warningAlert.setContentText("Password must be at least 6 characters long!");
			warningAlert.show();
		}
		else if(!password.equals(confirmPassword)) {
			warningAlert.setContentText("Confirm password doesn't match!");
			warningAlert.show();
		}
		else if(age <= 0) {
			warningAlert.setContentText("Age must be at least 1!");
			warningAlert.show();
		}
		else if(genderGroup.getSelectedToggle() == null) {
			warningAlert.setContentText("You must select at least 1  gender!");
			warningAlert.show();
		}
		else if(nationality.equals("Nationality")) {
			warningAlert.setContentText("You must select a nationality!");
			warningAlert.show();
		}
		// all valid -> insert user
		else {
			String gender = ((RadioButton)genderGroup.getSelectedToggle()).getText();
			String userID = "UA" + String.format("%03d", userTable.size() + 1);
			Database.insertUser(userID, email, password, age, gender, nationality, "User");
		}
	}
}
