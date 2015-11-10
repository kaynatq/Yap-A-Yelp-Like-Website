package yap.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import yap.sql.MySQLConnector;

public class YapReview {
	private static double total;
	private static int count;
	private Connection con;
	public YapReview() {}
	
	private ResultSet queryResult(String query) {
		ResultSet result = null;
		try {
			con = MySQLConnector.getConnection();

			// create a statement object
			Statement stmt = con.createStatement();

			// execute a query, which returns a ResultSet object
			result = stmt.executeQuery(query);
			if(result.wasNull()) {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;		
	}
	
	public ArrayList<String> viewAllReviews(ArrayList<Integer> reviewIDs, String businessID) {
		ArrayList<String> titleAndBody = new ArrayList<>();
		String query = "SELECT name FROM Business WHERE businessID='" + businessID + "'";
		String returnString = "";
		ResultSet result = queryResult(query);
		String name = "";
		String text = "";
		double rating;
		try {
			result.next();
			name = result.getString("name");
			con.close();
			titleAndBody.add("Yap :: " + name);
			returnString += "<table border=\"1\" cellpadding=\"10\">";
			returnString += "<h1>" + name + "</h1>";
			if (reviewIDs.isEmpty()) {
				returnString += "<tr><td>" + "No reviews" + "</td></tr>";
			}
			else {
				returnString += "<h2>Average Rating: " + getAverageRating(businessID) +  "</h2>"
						+ "<h2>Reviews</h2>";
				for (Integer reviewID : reviewIDs) {
					query = "SELECT userID, rating, text FROM Review WHERE reviewID = '" + reviewID + "'";
					result = queryResult(query);
					result.next();
					name = result.getString("userID");
					rating = result.getDouble("rating");
					text = result.getString("text");
					con.close();
					
					query = "SELECT name from User where userID = '" + name + "'";
					result = queryResult(query);
					result.next();
					name = result.getString("name");
					con.close();
					
					returnString += "<tr><td> " + name + "</td>" + "<td>Rating : " + rating + "</td></tr>" +
					"<tr><td colspan=\"2\">" + text + "</td></tr>";
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		returnString += "</table>";
		titleAndBody.add(returnString);
		return titleAndBody;
	}
	
	public ArrayList<Integer> getReviewIDs(String businessID) {
		ArrayList<Integer> reviewIDs = new ArrayList<>();
		String query = "SELECT * FROM Review WHERE businessID = '" + businessID + "'";
		ResultSet result = queryResult(query);
			total = 0.0;
			count = 0;
			try {
				while (result.next()) {
					reviewIDs.add(result.getInt("reviewID"));
					total += result.getDouble("rating");
					count ++;
				}
			con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			return reviewIDs;
	}
	
	public double getAverageRating(String businessID) {
		getReviewIDs(businessID);
		if (count == 0) return 0.0;
		return total/count;
	}
}