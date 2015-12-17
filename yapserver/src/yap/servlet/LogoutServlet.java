package yap.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STRawGroupDir;

import yap.utils.TemplateConstants;

/**
 * Servlet implementation class LogoutServlet
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		
		HttpSession session = request.getSession();

		String userId = (String) session.getAttribute("userid");
		String userName = (String) session.getAttribute("username");

		if (userId == null || userId.isEmpty() || userName == null || userName.isEmpty()) {
			response.getWriter().print(ServletUtils.getStatusPage(
					"..::YapServer::Logout::..",
					"<strong> Error! </strong> No logged in user was found.",
					"danger"));
			return;
		}

		session.setAttribute("userid", "");
		session.setAttribute("username", "");
		response.getWriter().print(ServletUtils.getStatusPage(
				"..::YapServer::Logout::..",
				"<strong> Success! </strong> Successfully logged out: " + userName,
				"success"));
	}
}
