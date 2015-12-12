package yap.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STRawGroupDir;

import yap.utils.TemplateConstants;

public class RootServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		
		STGroup templates = new STRawGroupDir("WebContent/Templates", '$', '$');
		
		ST jumboHeader = templates.getInstanceOf(TemplateConstants.JUMBO_HEADER_PAGE);		
		jumboHeader.add(TemplateConstants.HEADER_TITLE, "YapServer");
		jumboHeader.add(TemplateConstants.HEADER_TEXT, "The home made Yelp server.");
		
		ST body = templates.getInstanceOf(TemplateConstants.ROOT_BODY_PAGE);
		body.add("header", jumboHeader.render());
		
		ST rootPage = templates.getInstanceOf(TemplateConstants.FULL_PAGE);
		rootPage.add(TemplateConstants.TITLE, "..::Yap::Home..");
		rootPage.add(TemplateConstants.BODY, body.render());
		
		
		response.getWriter().append(rootPage.render());
	}
}
