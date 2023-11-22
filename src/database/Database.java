package database;

import java.util.ArrayList;
import objects.User;
import java.sql.*;

public class Database {
	
	// fetch all user
	public static ArrayList<User> fetchUser() {
		ArrayList<User> userTable = new ArrayList<>();
		
		Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/CLminton", "root", "");
 
            Statement statement;
            statement = connection.createStatement();
            ResultSet resultSet;
            resultSet = statement.executeQuery("select * from msuser");
            
            String userID, userEmail, userPassword, userGender, userNationality, userRole;
            int userAge;
            while (resultSet.next()) {
                userID = resultSet.getString("UserID");
                userEmail = resultSet.getString("UserEmail");
                userPassword = resultSet.getString("UserPassword");
                userAge = resultSet.getInt("UserAge");
                userGender = resultSet.getString("UserGender");
                userNationality = resultSet.getString("UserNationality");
                userRole = resultSet.getString("UserRole");
                userTable.add(new User(userID, userEmail, userPassword, userAge, userGender, userNationality, userRole));
            }
            resultSet.close();
            statement.close();
            connection.close();
        }
        catch (Exception exception) {
            System.out.println(exception);
        }
		
		return userTable;
	}
}
