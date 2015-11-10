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
import yap.sql.MySQLConnector;

/**
 * Servlet implementation class BusinessServlet
 */
@WebServlet("/business")
public class BusinessServlet extends HttpServlet {
	
	private static ArrayList<YapBusiness> getBusinesses() {
		ArrayList<YapBusiness> businesses = new ArrayList<>();	
		
		Connection con = null;
		try {
			con = MySQLConnector.getConnection();

			// create a statement object
			Statement stmt = con.createStatement();

			// execute a query, which returns a ResultSet object
			ResultSet result = stmt.executeQuery("SELECT * FROM Business");

			// iterate over the ResultSet
			if (result.wasNull()) {
				con.close();
				return null;
			}

			while (result.next()) {
				YapBusiness business = new YapBusiness();
				business.setBusinessID(result.getString("businessID"));
				System.out.println(business.getBusinessID());
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
		String returnString = "";
		returnString +=  "<table>";
		for(YapBusiness business : businesses) {
			returnString +=  "<tr><td>Business ID: " + business.getBusinessID() + "</td></tr>" +
				  "<tr><td>Business Name: " + business.getName() + "</td></tr>" +
				  "<tr><td>City: " + business.getCity() + "</td></tr>" +
				  "<tr><td>State: " + business.getState() + "</td></tr>";
		}
		returnString +=		"</table>";
		return returnString;
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		ArrayList<YapBusiness> businesses = getBusinesses();
		response.getWriter().println(ServletUtils.getHtmlForTitleAndBody(
				"Showing all business", viewAllBusiness(businesses)));
	}
}
