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

import yap.data.YapBusiness;
import yap.sql.MySQLConnector;
import yap.utils.SessionConstants;
import yap.utils.TemplateConstants;

/**
 * Servlet implementation class BusinessServlet
 */
@WebServlet("/addbusiness")
public class AddBusinessServlet extends HttpServlet {
	private static String TITLE = "..::Yap::AddBusiness..";

	private static String getInputFormHtml(String error) {
		STGroup templates = new STRawGroupDir("WebContent/Templates", '$', '$');

		ST body = templates.getInstanceOf(TemplateConstants.ADD_BUSINESS_PAGE);
		body.add("has_error", error != null);
		body.add("error_text", error == null ? "" : error);

		ST businessListPage = templates.getInstanceOf(TemplateConstants.FULL_PAGE);
		businessListPage.add(TemplateConstants.TITLE, AddBusinessServlet.TITLE);
		businessListPage.add(TemplateConstants.BODY, body.render());

		return businessListPage.render();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        
        HttpSession session = request.getSession();
		String userName = (String) session.getAttribute(SessionConstants.USERNAME);
		String userId = (String) session.getAttribute(SessionConstants.USERID);
		
		if (ServletUtils.isUserLoggedIn(userName, userId)) {
			response.getWriter().print(getInputFormHtml(null));
		} else {
			response.getWriter().print(ServletUtils.getStatusPage(
					AddBusinessServlet.TITLE,
					"You must be logged in to add a new business.",
					"danger"));
		}
	}
	
	private boolean IsValidLatLng(Double latitude, Double longitude) {
		if (latitude > -90.0 && latitude < 90.0 && longitude > -180.0 && longitude < 180.0) {
			return true;
		}
		return false;
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		
		String error = null;
		String businessName = request.getParameter("businessname");
		String city = request.getParameter("city");
		String state = request.getParameter("state");
		
		if (businessName == null || businessName.isEmpty()
				|| city == null || city.isEmpty()
				|| state == null || state.isEmpty()) {
			error = "Please provide non-empty (name, city, state)";
		}
		
		Double latitude = 0.0, longitude = 0.0;
		try{
			latitude = Double.parseDouble(request.getParameter("latitude"));
			longitude = Double.parseDouble(request.getParameter("longitude"));
			
			if (!IsValidLatLng(latitude, longitude)) {
				throw new NumberFormatException();
			}
		} catch(NumberFormatException ne) {
			error = "Please provide a valid (lat, lng)";
		}
		
		String neighborhoods = request.getParameter("neighborhoods");
		if (neighborhoods == null) {
			neighborhoods = "";
		}
		
		if (error != null) {
			// Re-Generate the form with error.
			response.getWriter().println(getInputFormHtml(error));
			return;
		}
		
		YapBusiness b = new YapBusiness();
		
		b.setName(businessName);
		b.setCity(city);
		b.setState(state);
		b.setLatitude(latitude);
		b.setLongitude(longitude);
		b.setNeighborhoods(neighborhoods);
		
		if (b.InsertToDB()) {
			response.getWriter().println(ServletUtils.getStatusPage(
					AddBusinessServlet.TITLE,
					"<strong>Success!</strong> Successfully added business to database.",
					"success"));
		} else {
			response.getWriter().println(ServletUtils.getStatusPage(
					AddBusinessServlet.TITLE,
					String.format("<strong>Error!</strong> Failed to add business '%s' to database.", businessName),
					"danger"));
		}
	}
}
