package yap.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RootServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		
		String body = "<body>"
				+ "<h1>Yapp Application</h1>"
				+ "<table>"
				+   "<tr><td><a href=\"login\">Login</a></td></tr>"
				+   "</tr><td><a href=\"signup\">SignUp</a></td></tr>"
				+ "</table>"
				+ "</body>";
		
		response.getWriter().println(ServletUtils.getHtmlForTitleAndBody("Yapp", body));
	}

}
