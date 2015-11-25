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
@WebServlet("/addbusiness")
public class AddBusinessServlet extends HttpServlet {
	
	private static String getInputFormHtml(String header, String error) {
		String form = "<h1>" + header + "</h1>"
				+ ServletUtils.getFormattedErrorString(error)
				+ "<form action=\"addbusiness\" method=\"post\">"
				+ "<table>"
				+   "<tr>"
				+     "<th>Business ID</th>"
				+     "<td><input type=\"text\" name=\"businessid\" ></td>"
				+   "</tr>"
				+   "<tr>"
				+     "<th>Business Name</th>"
				+     "<td><input type=\"text\" name=\"businessname\" ></td>"
				+   "</tr>"
				
				+   "<tr>"
				+     "<th>City</th>"
				+     "<td><input type=\"text\" name=\"city\" ></td>"
				+   "</tr>"
				
				+   "<tr>"
				+     "<th>State</th>"
				+     "<td><input type=\"text\" name=\"state\" ></td>"
				+   "</tr>"
				
				+   "<tr>"
				+     "<th>latitude</th>"
				+     "<td><input type=\"text\" name=\"latitude\" ></td>"
				+   "</tr>"
				
				+   "<tr>"
				+     "<th>longitude</th>"
				+     "<td><input type=\"text\" name=\"longitude\" ></td>"
				+   "</tr>"

				+   "<tr>"
				+     "<th>neighborhoods</th>"
				+     "<td><input type=\"text\" name=\"neighborhoods\" ></td>"
				+   "</tr>"

				+   "<tr>"
				+     "<td colspan=\"2\" align=\"center\"><input type=\"submit\" value=\"Add Business\"></td>"
				+   "</tr>"
				+ "</table>"
				+ "</form>";
		
		return form;
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(ServletUtils.getHtmlForTitleAndBody(
        		"Yap :: Add Business",
        		getInputFormHtml("Add a business", "")));
	}

	private String getBodyForSuccessfulAddBusiness(String businessID, String businessName, 
			String city, String state, double latitude, double longitude, String neighborhoods) {
		Connection con = null;
		Statement stmt = null;

		try {
			con = MySQLConnector.getConnection();

			// create a statement object
			stmt = con.createStatement();
			
			String msqlStatement = "INSERT INTO Business VALUES ("
			  + "'" + businessID + "',"
			  + "'" + businessName + "',"
			  + "'" + city + "',"
			  + "'" + state + "',"
			  + "'" + latitude + "',"
			  + "'" + longitude + "',"
			  + "'" + neighborhoods + "')";
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
				  "<tr><td>Business added successfully !</td></tr>" +		  
				"</table>";
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		
		String businessID = (String) request.getParameter("businessid");
		String businessName = (String) request.getParameter("businessname");
		String city = (String) request.getParameter("city");
		String state = (String) request.getParameter("state");
		double latitude = Double.parseDouble(request.getParameter("latitude"));
		double longitude = Double.parseDouble(request.getParameter("longitude"));
		String neighborhoods = (String) request.getParameter("neighborhoods");
		if (neighborhoods == null) {
			neighborhoods = "";
		}
				
		response.getWriter().println(ServletUtils.getHtmlForTitleAndBody(
					"Yap :: AddBusiness", getBodyForSuccessfulAddBusiness(businessID, businessName, city, state, latitude, longitude, neighborhoods)));			
		
	}	
}
