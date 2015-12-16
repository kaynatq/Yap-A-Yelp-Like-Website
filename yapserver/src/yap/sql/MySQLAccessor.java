package yap.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MySQLAccessor {
	private Connection con = null;
	private ResultSet results = null;
	private String error = "";
	
	public String getError() {
		return error;
	}
	
	public void InvokeQuery(String query) {
		try {
			if (con != null) // One accessor per query.
				return;

			con = MySQLConnector.getConnection();
			Statement stmt = con.createStatement();
			results = stmt.executeQuery(query);
		} catch (SQLException e) {
			error = e.getMessage();
			e.printStackTrace();
		}
	}
	
	public void InvokeParametrizedQuery(String query, String param) {
		try {
			if (con != null) // One accessor per query.
				return;
			
			if (param == null || query == null) return;

			con = MySQLConnector.getConnection();
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, param);
			results = ps.executeQuery();
		} catch (SQLException e) {
			error = e.getMessage();
			e.printStackTrace();
		}
	}
	
	public void Close() {
		if (con == null)
			return;
		try {
			if (!con.isClosed()) {
				con.close();
			}
		} catch (SQLException e) {
			error = e.getMessage();
			e.printStackTrace();
		}
	}
	
	public boolean Next() {
		if (results == null) return false;
		
		boolean success = false;
		
		try {
			success = results.next();
		} catch (SQLException e) {
			error = e.getMessage();
			e.printStackTrace();
		}
		
		return success;
	}
	
	private
	<T> T get(String label, Class<T> type) {
		if (results == null)
			return null;		
		T r = null;
		
		try {
			r = results.getObject(label, type);
		} catch (SQLException e) {
			error = e.getMessage();
			e.printStackTrace();
		}
		
		return r;
	}
	
	public double getDouble(String label) {
		Double r = get(label, Double.class);		
		if (r == null) return 0.0;
		
		return r;
	}
	
	public String getString(String label) {
		String s = get(label, String.class);
		if (s == null) return "";
		return s;
	}
	
	public Date getDate(String label) {
		Date d = new Date();
		
		String dateString = get(label, String.class);		
		if (dateString == null) return new Date();
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		try {
			d = df.parse(dateString);
		} catch (ParseException p) {
			p.printStackTrace();
		}
		
		return d;		
	}
	
	@Override
	public void finalize() {
		Close();
	}
	
	public static int getCount(String query, String param, String countLabel) {
		MySQLAccessor a = new MySQLAccessor();
		a.InvokeParametrizedQuery(query, param);
		Integer count = null;
		
		while (a.Next()) {
			count = a.get(countLabel, Integer.class);
			
			if (count != null)
				return count;
			
			break;
		}
		
		a.Close();
		return 0;
	}
}
