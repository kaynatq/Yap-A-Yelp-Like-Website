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

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STRawGroupDir;

import yap.sql.MySQLConnector;
import yap.utils.TemplateConstants;

/**
 * Servlet implementation class BusinessServlet
 */
@WebServlet("/addbusiness")
public class AddBusinessServlet extends HttpServlet {
	private static String getInputFormHtml() {
		STGroup templates = new STRawGroupDir("WebContent/Templates", '$', '$');
		
		ST body = templates.getInstanceOf(TemplateConstants.ADD_BUSINESS_PAGE);
		
		ST businessListPage = templates.getInstanceOf(TemplateConstants.FULL_PAGE);
		businessListPage.add(TemplateConstants.TITLE, "..::Yap::Business..");
		businessListPage.add(TemplateConstants.BODY, body.render());
		
		return businessListPage.render();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().print(getInputFormHtml());
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
		
		double latitude = 0.0, longitude = 0.0;
		String businessID = (String) request.getParameter("businessid");
		String businessName = (String) request.getParameter("businessname");
		String city = (String) request.getParameter("city");
		String state = (String) request.getParameter("state");
		if(businessID == null || businessID.isEmpty() || 
				businessName == null || businessName.isEmpty() ||
				city == null || city.isEmpty() ||
				state == null || state.isEmpty()) {
			response.getWriter().println(ServletUtils.getFormattedErrorString(
					"Please provide the empty fields"));		
			return;
		}
		try{
		latitude = Double.parseDouble(request.getParameter("latitude"));
		longitude = Double.parseDouble(request.getParameter("longitude"));
		}catch(NumberFormatException ne){
			response.getWriter().println(ServletUtils.getFormattedErrorString(
					"Please provide valid longitude and latitude"));		
			return;
		}
		String neighborhoods = (String) request.getParameter("neighborhoods");
		
		if (neighborhoods == null) {
			neighborhoods = "";
		}
					
		response.getWriter().println(ServletUtils.getHtmlForTitleAndBody(
				"Yap :: AddBusiness", getBodyForSuccessfulAddBusiness(businessID, businessName, city, state, latitude, longitude, neighborhoods)));			
			
	}	
}
