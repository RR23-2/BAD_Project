package pages;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import main.Main;

public class GlobalMenuBar {
	public static MenuBar globalMenuBar() {
		MenuBar globalMenuBar = new MenuBar();
		
		// page menu
		Menu pageMenu = new Menu("Page");
		MenuItem login = new MenuItem("Login");
		login.setOnAction(e -> Main.login());
		MenuItem register = new MenuItem("Register");
		register.setOnAction(e -> Main.register());
		pageMenu.getItems().addAll(login, register);

		// user menu
		Menu userMenu = new Menu("Page");
		MenuItem home = new MenuItem("Home");
		MenuItem cart = new MenuItem("Cart");
		MenuItem history = new MenuItem("History");
		MenuItem userLogout = new MenuItem("Logout");
		userMenu.getItems().addAll(home, cart, history, userLogout);

		// admin menu
		Menu adminMenu = new Menu("Admin");
		MenuItem manageProduct = new MenuItem("Manage Product");
		MenuItem viewHistory = new MenuItem("View History");
		MenuItem adminLogout = new MenuItem("Logout");
		adminMenu.getItems().addAll(manageProduct, viewHistory, adminLogout);

		globalMenuBar.getMenus().addAll(pageMenu, userMenu, adminMenu);
		
		return globalMenuBar;
	}
}
