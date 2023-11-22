package objects;

public class Product {
	public String productID, productName, productBrand;
	public int productPrice, productStock;
	public Product(String productID, String productName, String productBrand, int productPrice, int productStock) {
		super();
		this.productID = productID;
		this.productName = productName;
		this.productBrand = productBrand;
		this.productPrice = productPrice;
		this.productStock = productStock;
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
		return productStock;
	}
	public void setProductStock(int productStock) {
		this.productStock = productStock;
	}
	
}
