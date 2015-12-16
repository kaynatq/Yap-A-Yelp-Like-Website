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

import yap.data.YapUser;
import yap.utils.SessionConstants;
import yap.utils.TemplateConstants;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static String getBodyForLoginForm(String error) {
		STGroup templates = new STRawGroupDir("WebContent/Templates", '$', '$');

		ST body = templates.getInstanceOf(TemplateConstants.LOGIN_FORM_PAGE);
		if (error == null) {
			body.add("has_error", false);
		} else {
			body.add("has_error", true);
			body.add("error_text", error);
		}

		ST loginPage = templates.getInstanceOf(TemplateConstants.FULL_PAGE);
		loginPage.add(TemplateConstants.TITLE, "..::Yap::Login..");
		loginPage.add(TemplateConstants.BODY, body.render());

		return loginPage.render();
	}

	private static String getBodyForSuccessfulLogin(String userName) {
		STGroup templates = new STRawGroupDir("WebContent/Templates", '$', '$');

		ST body = templates.getInstanceOf(TemplateConstants.LOGIN_SUCCESS_PAGE);
		body.add("username", userName);

		ST loginPage = templates.getInstanceOf(TemplateConstants.FULL_PAGE);
		loginPage.add(TemplateConstants.TITLE, "..::Yap::Login..");
		loginPage.add(TemplateConstants.BODY, body.render());

		return loginPage.render();
	}

	private boolean isUserLoggedIn(String userName, String userId) {
		if (userId == null || userId.isEmpty())
			return false;
		if (userName == null || userName.isEmpty())
			return false;

		return true;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);

		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute(SessionConstants.USERNAME);
		String userID = (String) session.getAttribute(SessionConstants.USERID);

		if (!isUserLoggedIn(userName, userID)) {
			response.getWriter().print(getBodyForLoginForm(null));
		} else {
			response.getWriter().print(getBodyForSuccessfulLogin(userName));
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);

		String userID = request.getParameter("userid");
		String password = request.getParameter("password");
		
		if (userID == null || userID.isEmpty() || password == null || password.isEmpty()) {
			response.getWriter().print(getBodyForLoginForm("Empty Fields are not allowed."));
			return;
		}

		YapUser user = YapUser.getUserWithUserId(userID);
		if (user == null || !password.equals(user.getPassword())) {
			response.getWriter().print(getBodyForLoginForm("Invalid Login Information."));
			return;
		}

		HttpSession session = request.getSession();

		session.setAttribute(SessionConstants.USERID, user.getUserID());
		session.setAttribute(SessionConstants.USERNAME, user.getName());

		response.getWriter().print(getBodyForSuccessfulLogin(user.getName()));
	}
}