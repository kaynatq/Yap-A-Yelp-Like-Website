package yapp.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import yapp.sql.MySQLConnector;

public class YappUser {
	private String name;
	private  String userID;
	private  String password;
	
	public YappUser() {
		name = null;
		userID = null;
		password = null;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isValid() {
		if (userID == null || userID.isEmpty()) return false;
		if (name == null || name.isEmpty()) return false;
		if (password == null || password.isEmpty()) return false;
		
		return true;
	}
	
	public static YappUser getUserWithUserId(String userID) {
		YappUser user = new YappUser();		
		Connection con = null;
		try {
			con = MySQLConnector.getConnection();

			// create a statement object
			Statement stmt = con.createStatement();

			// execute a query, which returns a ResultSet object
			ResultSet result = stmt.executeQuery("SELECT * FROM User WHERE userID = '" + userID + "'");

			// iterate over the ResultSet
			if (result.wasNull()) {
				con.close();
				return null;
			}

			while (result.next()) {
				user.setUserID(result.getString("userID"));
				user.setName(result.getString("name"));
				user.setPassword(result.getString("password"));
			}
			
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return user;
	}
	
	public boolean addToDB() throws SQLException {
		Connection con = null;
		
		con = MySQLConnector.getConnection();
		Statement stmt = con.createStatement();

		// execute an update.
		int affected_rows = stmt.executeUpdate("INSERT INTO User (userID, name, password) VALUES ("
		  + "'" + getUserID() + "', "
		  +	"'"	+ getName() + "', "
		  + "'" + getPassword() + "' "
		  + ")");

		con.close();

		return affected_rows == 1;
	}	
}