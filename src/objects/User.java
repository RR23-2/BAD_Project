package objects;

public class User {
	public String userID, userEmail, userPassword, userGender, userNationality, userRole;
	public int userAge;
	public User(String userID, String userEmail, String userPassword, int userAge, String userGender, String userNationality,
			String userRole) {
		super();
		this.userID = userID;
		this.userEmail = userEmail;
		this.userPassword = userPassword;
		this.userAge = userAge;
		this.userGender = userGender;
		this.userNationality = userNationality;
		this.userRole = userRole;
	}
}
