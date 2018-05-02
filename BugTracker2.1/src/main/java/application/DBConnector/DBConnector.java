package application.DBConnector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DBConnector {
	private static Connection connection;
	
	public static Connection getConnection(){
		connection = null;
		ResourceBundle resource = ResourceBundle.getBundle("Connection");
		String url = resource.getString("url");
		String login = resource.getString("login");
		String password = resource.getString("password");
		
		try {
		connection =  DriverManager.getConnection(url,login,password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
		
	}
	
	public static void closeConnection(){
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace(System.out);
		}
	}

}
