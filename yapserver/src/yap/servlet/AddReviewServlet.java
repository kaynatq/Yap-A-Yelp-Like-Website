package yap.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import yap.sql.MySQLConnector;

/**
 * Servlet implementation class BusinessServlet
 */
@WebServlet("/addreview")
public class AddReviewServlet extends HttpServlet {
	
	private static String getInputFormHtml(String header, String error, String businessID) {
		String form = "<h1>" + header + "</h1>"
				+ ServletUtils.getFormattedErrorString(error)
				+ "<form action=\"addreview\" method=\"post\">"
				+ "<input type=\"hidden\" name=\"businessID\" value=\"" + businessID + "\">"
				+ "<table>"
				+   "<tr>"
				+     "<th>Write a Review</th>"
				+     "<td><input type=\"text\" name=\"reviewtext\" ></td>"
				+   "</tr>"
				+   "<tr>"
				+     "<th>Rating</th>"
				+     "<td><input type=\"number\" name=\"rating\" min=\"1\" max=\"5\"></td>"
				+   "</tr>"
				+   "<tr>"
				+     "<td colspan=\"2\" align=\"center\"><input type=\"submit\" value=\"AddReview\"></td>"
				+   "</tr>"
				+ "</table>"
				+ "</form>";
		
		return form;
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String businessId = request.getParameter("businessID");
		response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(ServletUtils.getHtmlForTitleAndBody(
        		"Yap :: Add Review",
        		getInputFormHtml("Add a review", "", businessId)));
	}

	private String getBodyForSuccessfulAddReview(String text, double rating, String userID, String businessID) {
		Connection con = null;
		Statement stmt = null;
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			con = MySQLConnector.getConnection();

			// create a statement object
			stmt = con.createStatement();
			
			String msqlStatement = "INSERT INTO Review (rating, date, userID, text, businessID) VALUES ("
			  + "'" + rating + "',"
			  + "'" + df.format(new Date()) + "',"
			  + "'" + userID + "',"
			  + "'" + text + "',"
			  + "'" + businessID + "')";
			System.out.println(msqlStatement);
			
			// execute an update.
			stmt.executeUpdate(msqlStatement);

			con.close();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  "<table>" +
				  "<tr><td>Review added successfully !</td></tr>" +		  
				"</table>";
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		
		HttpSession session = request.getSession();
		String userID = (String) session.getAttribute("userid");
		
		double rating = 0.0;
		String text = (String) request.getParameter("reviewtext");
		String ratingParameter = (String) request.getParameter("rating");
		if (!ratingParameter.isEmpty() && ratingParameter != null)
			rating = Double.parseDouble(request.getParameter("rating"));
		String businessID = (String) request.getParameter("businessID");
		
		if (text == null || text.isEmpty() || rating > 5 || rating < 1 || rating == 0.0) {
			if (text == null || text.isEmpty()) {
			response.getWriter().println(ServletUtils.getHtmlForTitleAndBody(
					"Yap :: AddReview",
					getInputFormHtml("Adding review", "Please write a review", businessID)));
			}
			else {
				response.getWriter().println(ServletUtils.getHtmlForTitleAndBody(
						"Yap :: AddReview",
						getInputFormHtml("Adding review", "Please provide a rating between 1 to 5", businessID)));
			}
		}		
		
		else {
			response.getWriter().println(ServletUtils.getHtmlForTitleAndBody(
					"Yap :: AddReview", getBodyForSuccessfulAddReview(text, rating, userID, businessID)));			
		}
	}	
}
