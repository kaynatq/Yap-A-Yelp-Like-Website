package yap.servlet;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STRawGroupDir;

import yap.utils.TemplateConstants;

public class ServletUtils {
	public static String getHtmlForTitleAndBody(String title, String body) {
		return "<!DOCTYPE html>"
				+ "<html>"
				+ "<head>"
				+   "<meta charset=\"UTF-8\">"
				+   "<title>" + title + "</title>"
				+ "</head>"
				+ "<body>" + body + "</body>"
				+ "</html>";
	}

	public static String getFormattedErrorString(String error) {
		return error.isEmpty() ? "" : "<div style=\"color:#FF0000\">" + error + "</div>";
	}

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
	
	

}
