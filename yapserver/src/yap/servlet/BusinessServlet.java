package yap.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STRawGroupDir;

import yap.data.YapBusiness;
import yap.data.YapReview;
import yap.sql.MySQLAccessor;
import yap.sql.MySQLConnector;
import yap.utils.TemplateConstants;

/**
 * Servlet implementation class BusinessServlet
 */
@WebServlet("/business")
public class BusinessServlet extends HttpServlet {
	private static String viewAllBusiness(ArrayList<YapBusiness> businesses) {
		STGroup templates = new STRawGroupDir("WebContent/Templates", '$', '$');

		ST body = templates.getInstanceOf(TemplateConstants.BUSINESS_LIST_PAGE);
		body.add("businesses", businesses);

		ST businessListPage = templates.getInstanceOf(TemplateConstants.FULL_PAGE);
		businessListPage.add(TemplateConstants.TITLE, "..::Yap::Business..");
		businessListPage.add(TemplateConstants.BODY, body.render());

		return businessListPage.render();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);

		String sortby = request.getParameter("sortby");
		ArrayList<YapBusiness> businesses = YapBusiness.getBusinesses(sortby);
		for (YapBusiness b : businesses) {
			b.setRating(YapReview.getRatingForBusiness(b.getBusinessID()));
		}
		response.getWriter().print(viewAllBusiness(businesses));
	}
}
