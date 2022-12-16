package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

public class DatabaseManager {

	public static void mySQLJDBC () {
		try {
			String databaseUser = "root";
			String databaseUserPass = "";
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = null;
			String url = "jdbc:mysql://localhost/Messaging";
			connection = DriverManager.getConnection(url,databaseUser,databaseUserPass);
			Statement s = connection.createStatement();
			ResultSet rs = s.executeQuery("SELECT * from users");
			
			while (rs.next()) {
				System.out.println(rs.getString("name"));
			}
			rs.close();
			
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Login error: " + e.toString());
		}
	}
	
	public static void addMessage (String time, String sender, String receiver, String message) {
		try {
			//Log into database: the localhost XAMPP one
//			String databaseUser = "root";
//			String databaseUserPass = "";
//			Class.forName("com.mysql.cj.jdbc.Driver");
//			Connection connection = null;
//			String url = "jdbc:mysql://localhost/Messaging";

			//Azure MySQL database
			String databaseUser = "baramey";
			String databaseUserPass = "Wellington123!";
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = null;
			String url = "jdbc:mysql://baramey.mysql.database.azure.com:3306/quickstartdb?useSSL=true&requireSSL=false";
			
			connection = DriverManager.getConnection(url,databaseUser,databaseUserPass);
			//message into mySQL
			String sql = " INSERT into messages (timeSent, userID, receiverID, message )" + 
					" values (?, ?, ?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, time);
			preparedStatement.setString(2, sender);
			preparedStatement.setString(3, receiver);
			preparedStatement.setString(4, message);
			//send the prepared statement
			preparedStatement.execute();
			connection.close();
			System.out.println("Sent to azure");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Login error: " + e.toString());
		}

	}
	
	public static boolean checkLogin (String username, String password) {
		boolean correctLogin = false; 
		String presentUsername = null;
		
		try {
			

			//Azure MySQL database
			String databaseUser = "baramey";
			String databaseUserPass = "Wellington123!";
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = null;
			String url = "jdbc:mysql://baramey.mysql.database.azure.com:3306/quickstartdb?useSSL=true&requireSSL=false";
			
			connection = DriverManager.getConnection(url,databaseUser,databaseUserPass);
			//message into mySQL
			String sql = "select * from users where username ='" + username + "' and password = '" +
					password + "'";

			Statement s = connection.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			while (rs.next()) {
				presentUsername = rs.getString("username");
				System.out.println("Username: " + presentUsername);		
			//	System.out.println(rs.getString("password"));
				
			}
			rs.close();

			connection.close();
			System.out.println("Sent to azure");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Login error: " + e.toString());
		}
		if (presentUsername != null) {
			correctLogin = true;
		}
		return correctLogin;

	}
	
	

	public static void main(String[] args) {
	//	mySQLJDBC ();
	//	addMessage (new Date().toString() , "Barry", "Andy", "Hi Andy, my name is Barry");
	//	checkLogin ("Barry", "1234");
		if (checkLogin ("Barry", "1234")) {
			System.out.println("User details are correct");
		} else {
			System.out.println("User is not present");
		}
	}

}
