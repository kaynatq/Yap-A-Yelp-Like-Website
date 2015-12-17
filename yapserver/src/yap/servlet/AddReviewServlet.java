package yap.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STRawGroupDir;

import yap.data.YapBusiness;
import yap.data.YapReview;
import yap.utils.TemplateConstants;

/**
 * Servlet implementation class BusinessServlet
 */
@WebServlet("/addreview")
public class AddReviewServlet extends HttpServlet {
	private static String TITLE = "..::Yap::AddReview..";
	
	private String getAddReviewPageForBusiness(YapBusiness biz, String error) {
		STGroup templates = new STRawGroupDir("WebContent/Templates", '$', '$');

		ST businessHeader = templates.getInstanceOf(TemplateConstants.BUSINESS_HEADER_PAGE);
		businessHeader.add("business", biz);

		ST body = templates.getInstanceOf(TemplateConstants.ADD_REVIEW_PAGE);
		body.add(TemplateConstants.BUSINESS_HEADER_PAGE, businessHeader.render());
		body.add("has_error", error != null);
		body.add("error_text", error == null ? "" : error);
		body.add("businessid", biz.getBusinessID());

		ST addReviewPage = templates.getInstanceOf(TemplateConstants.FULL_PAGE);
		addReviewPage.add(TemplateConstants.TITLE, AddReviewServlet.TITLE);
		addReviewPage.add(TemplateConstants.BODY, body.render());

		return addReviewPage.render();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        
        HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userid");
		
		if (userId == null) {
			response.getWriter().print(ServletUtils.getStatusPage(
					AddReviewServlet.TITLE,
					"<strong>Error!</strong> Please login to add review",
					"danger"));
			return;
		}
		
		String businessId = request.getParameter("businessid");
		if (businessId == null) {
			response.getWriter().print(ServletUtils.getStatusPage(
					AddReviewServlet.TITLE,
					"<strong>Error!</strong> No business to add review for.",
					"danger"));
			return;
		}

		YapBusiness b = YapBusiness.getBusinessWithBusinessId(businessId);
		if (b == null) {
			response.getWriter().print(ServletUtils.getStatusPage(
					AddReviewServlet.TITLE,
					"<strong>Error!</strong> Could not find business with ID: '" + businessId + "'",
					"danger"));
			return;
		}

        response.getWriter().print(getAddReviewPageForBusiness(b, null));
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		
		
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userid");
		
		if (userId == null) {
			response.getWriter().print(
					ServletUtils.getStatusPage(
							AddReviewServlet.TITLE,
							"<strong>Error!</strong> Please login to add review.",
							"danger"));
			return;
		}

		String text = request.getParameter("reviewtext");
		String businessId = request.getParameter("businessid");
		Double rating = null;
		try {
			rating = Double.parseDouble(request.getParameter("rating"));
		} catch (NumberFormatException ne) {
			ne.printStackTrace();
		}
		if (rating == null) {
			response.getWriter().print(getAddReviewPageForBusiness(
					YapBusiness.getBusinessWithBusinessId(businessId),
					"Empty rating is not allowed."));
			return;
		}
		

		YapReview r = new YapReview();
		r.setBusinessId(businessId);
		r.setRating(rating);
		r.setReviewDate(new Date());
		r.setText(text == null ? "" : text);
		r.setUserId(userId);

		if (!r.InsertToDB()) {
			response.getWriter().print(ServletUtils.getStatusPage(
					AddReviewServlet.TITLE,
					"<strong> Error! </strong> Failed adding review to database.",
					"danger"));
			return;
		}

		response.getWriter().print(ServletUtils.getStatusPage(
				AddReviewServlet.TITLE,
				"<strong> Success! </strong> Added review to database.",
				"success"));
	}
}
