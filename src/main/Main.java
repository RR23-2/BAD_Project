package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import scenes.LoginScene;

public class Main extends Application{
	
	static Scene mainScene;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public static void login(Stage stage) {
		mainScene = LoginScene.loginScene(stage);
		stage.setTitle("Login");
	}
	
	public static void home(Stage stage) {
		
		stage.setTitle("Home");
	}
	
	public static void editProduct(Stage stage) {
		
		stage.setTitle("Edit Product");
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		login(stage);
		stage.setScene(mainScene);
		stage.show();
	}
	
}
