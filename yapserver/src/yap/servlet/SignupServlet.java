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
        		"Yap :: Signup",
        		getInputFormHtml("Signup for Yap", "")));
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
        			"Yap :: Signup", getInputFormHtml("Signup for Yap", "Empty fields are not allowed.")));
        	return;
        }

        if (newUser.getPassword().length() < 8) {
        	response.getWriter().println(ServletUtils.getHtmlForTitleAndBody(
        			"Yap :: Signup", getInputFormHtml("Signup for Yap", "Password should be minimum 8 characters.")));
        	return;
        }

        if (!newUser.containsUpperLowerDigitSpChar()) {
        	response.getWriter().println(ServletUtils.getHtmlForTitleAndBody(
        			"Yap :: Signup", getInputFormHtml("Signup for Yap", "Password should contain at least "
        					+ "1 uppercase letter, 1 lowercase letter, 1 digit and 1 special character. ")));
        	return;
        }
        String body = "";

        try {
        	if (newUser.addToDB()) {
        		body = "Successfully added '" + newUser.getName() + "' as a Yap user."
        				+ "<br>"
        				+ "You can try logging in now.";
        	}
		} catch (SQLException e) {
			body = ServletUtils.getFormattedErrorString("Error adding user: " + e.getMessage());
		}

        response.getWriter().println(ServletUtils.getHtmlForTitleAndBody("Yap :: Signup", body));
	}

}
