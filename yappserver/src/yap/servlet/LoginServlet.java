package yap.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import yap.data.YapUser;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static String getInputFormHtml(String header, String error) {
		String form = "<h1>" + header + "</h1>"
				+ ServletUtils.getFormattedErrorString(error)
				+ "<form action=\"login\" method=\"post\">"
				+ "<table>"
				+   "<tr>"
				+     "<th>User Id</th>"
				+     "<td><input type=\"text\" name=\"userid\" ></td>"
				+   "</tr>"
				+   "<tr>"
				+     "<th>Password</th>"
				+     "<td><input type=\"password\" name=\"password\" ></td>"
				+   "</tr>"
				+   "<tr>"
				+     "<td colspan=\"2\" align=\"center\"><input type=\"submit\" value=\"Login\"></td>"
				+   "</tr>"
				+ "</table>"
				+ "</form>";
		
		return form;
	}
	
	private static String getBodyForSuccessfulLogin(String userName) {
		return  "<table>" +
				  "<tr><td>Logged in as: " + userName + "</td></tr>" +
				  "<tr><td align=\"center\"><a href=\"logout\">Log Out</a></td></tr>" +
				"</table>";
	}
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		
		HttpSession session = request.getSession();
		
		String userName = (String) session.getAttribute("username");
		String userID = (String) session.getAttribute("userid");
		
		if (userID == null || userID.isEmpty() || userName == null || userName.isEmpty()) {
			response.getWriter().println(ServletUtils.getHtmlForTitleAndBody(
					"Yapp :: Login",
					getInputFormHtml("Login to Yapp", "")));
		} else {
			response.getWriter().println(ServletUtils.getHtmlForTitleAndBody(
					"Yapp :: Login", getBodyForSuccessfulLogin(userName)));			
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        
        String userID = request.getParameter("userid");
        String password = request.getParameter("password");
        
        if (userID == null || userID.isEmpty() || password == null || password.isEmpty()) {
        	response.getWriter().println(ServletUtils.getHtmlForTitleAndBody(
					"Yapp :: Login",
					getInputFormHtml("Login to Yapp", "Empty Fields are not allowed.")));
        	return;
        }
        
        YapUser user = YapUser.getUserWithUserId(userID);
        
        if (user == null || !password.equals(user.getPassword())) {
        	response.getWriter().println(ServletUtils.getHtmlForTitleAndBody(
					"Yapp :: Login",
					getInputFormHtml("Login to Yapp", "Invalid Login Information.")));
        	return;
        } else {
        	HttpSession session = request.getSession();
        	
        	session.setAttribute("userid", user.getUserID());
        	session.setAttribute("username", user.getName());
        	
        	response.getWriter().println(ServletUtils.getHtmlForTitleAndBody(
					"Yapp :: Login", getBodyForSuccessfulLogin(user.getName())));
        }
	}

}
