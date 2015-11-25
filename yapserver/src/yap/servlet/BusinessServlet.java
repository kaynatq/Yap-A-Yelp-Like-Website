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

import yap.data.YapBusiness;
import yap.data.YapReview;
import yap.sql.MySQLConnector;

/**
 * Servlet implementation class BusinessServlet
 */
@WebServlet("/business")
public class BusinessServlet extends HttpServlet {
	
	private static ArrayList<YapBusiness> getBusinesses(String sortby) {
		ArrayList<YapBusiness> businesses = new ArrayList<>();
		Connection con = null;
		try {
			con = MySQLConnector.getConnection();

			// create a statement object
			Statement stmt = con.createStatement();
			
			String query = "SELECT * FROM Business";
			
			if (sortby != null) {
				query = query + " ORDER BY " + sortby;
			}

			// execute a query, which returns a ResultSet object
			ResultSet result = stmt.executeQuery(query);

			// iterate over the ResultSet
			if (result.wasNull()) {
				con.close();
				return null;
			}

			while (result.next()) {
				YapBusiness business = new YapBusiness();
				business.setBusinessID(result.getString("businessID"));
				business.setName(result.getString("name"));
				business.setCity(result.getString("city"));
				business.setState(result.getString("state"));
				business.setNeighborhoods((result.getString("neighborhoods")));
				business.setLongitude((result.getDouble("longitude")));
				business.setLatitude(result.getDouble("latitude"));
				businesses.add(business);
			}	
			
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return businesses;
	}

	private static String viewAllBusiness(ArrayList<YapBusiness> businesses) {
		YapReview yr = new YapReview();
		String returnString = "";
		returnString +=  "<table border=\"1\" cellpadding=\"10\">";
		returnString += "<tr>" +
				"<th><a href=\"/business?sortby=name\"> Business Name </a></th>" +
				"<th><a href=\"/business?sortby=city\"> City </a></th>" +
				"<th><a href=\"/business?sortby=state\"> State </a></th>" +
				"<th>Rating</th>" + "</tr>";
		for(YapBusiness business : businesses) {
			returnString +=  "<tr>"
					+ "<td><a href=\"reviews?businessID=" + business.getBusinessID()
						+ "\">" + business.getName() + "</a></td>"
					+ "<td>" + business.getCity() + "</td>"
					+ "<td>" + business.getState() + "</td>"
					+ "<td>" + yr.getAverageRating(business.getBusinessID()) + "</td>"
					+ "</tr>";
		}
		returnString +=		"</table>";
		returnString += "<table><tr><td><a href=\"addbusiness\">Add Business</a></td></tr></table>";
		return returnString;
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		
		String sortby = request.getParameter("sortby");
		
		ArrayList<YapBusiness> businesses = getBusinesses(sortby);
		response.getWriter().println(ServletUtils.getHtmlForTitleAndBody(
				"Showing all businesses", viewAllBusiness(businesses)));
	}
}
