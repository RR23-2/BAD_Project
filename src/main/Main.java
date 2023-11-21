package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import scenes.LoginScene;

public class Main extends Application{
	
	Scene mainScene;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void Login(Stage stage) {
		mainScene = LoginScene.loginScene();
		stage.setTitle("Login");
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		Login(stage);
		stage.setScene(mainScene);
		stage.show();
	}
	
}
