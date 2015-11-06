package yap.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import yap.data.YapUser;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
	private static String getInputFormHtml(String header, String error) {
		String form = "<h1>" + header + "</h1>"
				+ ServletUtils.getFormattedErrorString(error)
				+ "<form action=\"signup\" method=\"post\">"
				+ "<table>"
				+   "<tr>"
				+     "<th>User Id</th>"
				+     "<td><input type=\"text\" name=\"userid\" ></td>"
				+   "</tr>"
				+   "<tr>"
				+     "<th>Name</th>"
				+     "<td><input type=\"text\" name=\"username\" ></td>"
				+   "</tr>"
				+   "<tr>"
				+     "<th>Password</th>"
				+     "<td><input type=\"password\" name=\"password\" ></td>"
				+   "</tr>"
				+   "<tr>"
				+     "<td colspan=\"2\" align=\"center\"><input type=\"submit\" value=\"Signup\"></td>"
				+   "</tr>"
				+ "</table>"
				+ "</form>";
		
		return form;
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(ServletUtils.getHtmlForTitleAndBody(
        		"Yapp :: Signup",
        		getInputFormHtml("Signup for Yapp", "")));
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        
        YapUser newUser = new YapUser();        
        
        newUser.setUserID(request.getParameter("userid"));
        newUser.setName(request.getParameter("username"));
        newUser.setPassword(request.getParameter("password"));
        
        if (!newUser.isValid()) {
        	response.getWriter().println(ServletUtils.getHtmlForTitleAndBody(
        			"Yapp :: Signup", getInputFormHtml("Signup for Yapp", "Empty fields are not allowed.")));
        	return;
        }
        
        String body = "";
        try {
        	if (newUser.addToDB()) {
        		body = "Successfully added '" + newUser.getName() + "' as a Yapp user."
        				+ "<br>"
        				+ "You can try logging in now.";
        	}
		} catch (SQLException e) {
			body = ServletUtils.getFormattedErrorString("Error adding user: " + e.getMessage());
		}
        
        response.getWriter().println(ServletUtils.getHtmlForTitleAndBody("Yapp :: Signup", body));
	}

}
