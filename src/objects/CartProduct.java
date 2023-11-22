package objects;

public class CartProduct {
	public String productID, productName, productBrand;
	public int productPrice, quantity, total;
	public CartProduct(String productID, String productName, String productBrand, int productPrice, int quantity) {
		super();
		this.productID = productID;
		this.productName = productName;
		this.productBrand = productBrand;
		this.productPrice = productPrice;
		this.quantity = quantity;
		total = quantity * productPrice;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getProductID() {
		return productID;
	}
	public void setProductID(String productID) {
		this.productID = productID;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductBrand() {
		return productBrand;
	}
	public void setProductBrand(String productBrand) {
		this.productBrand = productBrand;
	}
	public int getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}
	public int getProductStock() {
		return quantity;
	}
	public void setProductStock(int productStock) {
		this.quantity = productStock;
	}
}
