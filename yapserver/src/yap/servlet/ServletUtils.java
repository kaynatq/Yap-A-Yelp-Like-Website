package yap.servlet;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STRawGroupDir;

import yap.utils.TemplateConstants;

public class ServletUtils {

	static String getStatusPage(String title, String statusText, String statusType) {
		STGroup templates = new STRawGroupDir("WebContent/Templates", '$', '$');
	
		ST body = templates.getInstanceOf(TemplateConstants.STATUS_LINE);
		body.add(TemplateConstants.STATUS_TEXT, statusText);
		body.add(TemplateConstants.STATUS_TYPE, statusType);
	
		ST statusPage = templates.getInstanceOf(TemplateConstants.FULL_PAGE);
		statusPage.add(TemplateConstants.TITLE, title);
		statusPage.add(TemplateConstants.BODY, body.render());
	
		return statusPage.render();
	}
	
	public static boolean isUserLoggedIn(String userName, String userId) {
		if (userId == null || userId.isEmpty())
			return false;
		if (userName == null || userName.isEmpty())
			return false;

		return true;
	}
}
