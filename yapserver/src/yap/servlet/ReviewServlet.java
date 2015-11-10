package yap.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import yap.sql.MySQLConnector;

/**
 * Servlet implementation class BusinessServlet
 */
@WebServlet("/reviews" )
public class ReviewServlet extends HttpServlet {
	private static double total;
	private static int count;
	private static ArrayList<String> getReviews(String businessID) {
		ArrayList<String> reviewText = new ArrayList<>();
		Connection con = null;
		try {
			con = MySQLConnector.getConnection();

			// create a statement object
			Statement stmt = con.createStatement();

			// execute a query, which returns a ResultSet object
			ResultSet result = stmt.executeQuery("SELECT * FROM Review WHERE businessID = '"
					+ businessID + "'");

			// iterate over the ResultSet
			if (result.wasNull()) {
				con.close();
				return null;
			}

			total = 0.0;
			count = 0;
			while (result.next()) {
				reviewText.add(result.getString("text"));
				total += result.getDouble("rating");
				count ++;
			}	
			
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return reviewText;
	}

	private static String viewAllReviews(ArrayList<String> texts) {
		String returnString = "";
		returnString +=  "<table>";
		if (texts.isEmpty()) {
			returnString += "<tr><td>" +
					"No reviews" +
					"</td></tr>";
		}
		for(String text: texts) {
			returnString +=  "<tr><td>" + text + "</td></tr>";
		}
		returnString +=  "<tr><td>" + "Average rating: " + total/count + "</td></tr>";
		returnString +=		"</table>";
		return returnString;
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		ArrayList<String> texts = getReviews(request.getParameter("businessID"));
		response.getWriter().println(ServletUtils.getHtmlForTitleAndBody(
				"Showing all reviews", viewAllReviews(texts)));
	}
}
