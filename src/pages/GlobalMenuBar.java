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
		home.setOnAction(e -> Main.home());
		MenuItem cart = new MenuItem("Cart");
		cart.setOnAction(e -> Main.cart());
		MenuItem history = new MenuItem("History");
		history.setOnAction(e -> Main.history());
		MenuItem userLogout = new MenuItem("Logout");
		userLogout.setOnAction(e -> Main.logout());
		userMenu.getItems().addAll(home, cart, history, userLogout);

		// admin menu
		Menu adminMenu = new Menu("Admin");
		MenuItem manageProduct = new MenuItem("Manage Product");
		manageProduct.setOnAction(e -> Main.manageProduct());
		MenuItem viewHistory = new MenuItem("View History");
		viewHistory.setOnAction(e -> Main.viewHistory());
		MenuItem adminLogout = new MenuItem("Logout");
		adminLogout.setOnAction(e -> Main.logout());
		adminMenu.getItems().addAll(manageProduct, viewHistory, adminLogout);

		globalMenuBar.getMenus().addAll(pageMenu, userMenu, adminMenu);
		
		return globalMenuBar;
	}
}
