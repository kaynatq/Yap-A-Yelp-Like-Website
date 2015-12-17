package yap.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STRawGroupDir;

import yap.data.YapUser;
import yap.utils.TemplateConstants;

/**
 * Servlet implementation class SignUpServlet
 */
@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
	private static String getSignupFormPage(String error) {
		STGroup templates = new STRawGroupDir("WebContent/Templates", '$', '$');

		ST body = templates.getInstanceOf(TemplateConstants.SIGNUP_FORM_PAGE);
		if (error == null) {
			body.add("has_error", false);
		} else {
			body.add("has_error", true);
			body.add("error_text", error);
		}

		ST signupPage = templates.getInstanceOf(TemplateConstants.FULL_PAGE);
		signupPage.add(TemplateConstants.TITLE, "..::Yap::SignUp..");
		signupPage.add(TemplateConstants.BODY, body.render());

		return signupPage.render();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().print(getSignupFormPage(null));
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
        	response.getWriter().print(getSignupFormPage("Empty fields are not allowed."));
        	return;
        }

        if (newUser.getPassword().length() < 6) {
        	response.getWriter().print(getSignupFormPage("Password should be minimum 6 characters."));
        	return;
        }

        if (!newUser.containsUpperLowerDigitSpChar()) {
        	response.getWriter().print(getSignupFormPage("Password should contain at least "
        					+ "1 uppercase letter, 1 lowercase letter, 1 digit or 1 special character. "));
        	return;
        }
        String body = "";
        
		if (newUser.addToDB()) {
			body = "Successfully added '" + newUser.getName() + "' as a Yap user. Try logging in now.";
			response.getWriter().print(ServletUtils.getStatusPage(
					"..::Yap :: Signup::..",
					"<strong> Success! </strong>" + body,
					"success"));
			return;
		} else {
			response.getWriter().print(getSignupFormPage("Error adding user: duplicate user found"));
			return;
		}
	}

}