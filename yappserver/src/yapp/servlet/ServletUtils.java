package yapp.servlet;

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

}
