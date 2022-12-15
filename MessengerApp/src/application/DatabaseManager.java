package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseManager {

	public static void mySQLJDBC () {
		try {
			String databaseUser = "root";
			String databaseUserPass = "";
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = null;
			String url = "jdbc:mysql://localhost/Messaging";
			connection = DriverManager.getConnection(url,databaseUser,databaseUserPass);
			String sql = " INSERT into messages (timeSent, userID, receiverID, message )" + 
					" values (?, ?, ?, ?)";
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			
			
//			Statement s = connection.createStatement();
//			ResultSet rs = s.executeQuery("SELECT * from users");
//			
//			while (rs.next()) {
//				System.out.println(rs.getString("timeSent"));
//			}
//			rs.close();
//			ResultSet rs = s.executeQuery("");
			
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Login error: " + e.toString());
		}
	}
	
	public static void addMessage (String time, String sender, String receiever, String message) {

	}
	
	

	public static void main(String[] args) {
		mySQLJDBC ();
	}

}
