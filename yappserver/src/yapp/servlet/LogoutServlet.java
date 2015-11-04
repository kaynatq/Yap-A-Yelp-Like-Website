package yapp.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
			response.getWriter().println(ServletUtils.getHtmlForTitleAndBody(
					"Yapp :: LogOut",
					ServletUtils.getFormattedErrorString("No logged-in user found")));
			return;
		}
		
		session.setAttribute("userid", "");
		session.setAttribute("username", "");		
		response.getWriter().println("Successfully Logged Out : " + userName);
	}
}
