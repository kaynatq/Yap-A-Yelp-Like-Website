package yap.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnector {
	// TODO: Get this from a config file.
	private static final String USERNAME = "root";
	private static final String PASSWORD = "potafied";
	private static final String DATABASE = "user49";
	private static final String MYSQL_HOST = "127.0.0.1";
	
	public static Connection getConnection() throws SQLException {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();

			String urlString = "jdbc:mysql://" + MYSQL_HOST + "/" + DATABASE;
			con = DriverManager.getConnection(urlString, USERNAME, PASSWORD);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return con;
	}
}