package objects;

public class Transaction {
	public String transactionID, userEmail, transactionDate;
	public Transaction(String transactionID, String customerEmail, String transactionDate) {
		super();
		this.transactionID = transactionID;
		this.userEmail = customerEmail;
		this.transactionDate = transactionDate;
	}
	public String getTransactionID() {
		return transactionID;
	}
	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String customerEmail) {
		this.userEmail = customerEmail;
	}
	public String getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
}
