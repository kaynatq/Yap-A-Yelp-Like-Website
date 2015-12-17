package yap.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STRawGroupDir;

import yap.data.YapBusiness;
import yap.data.YapReview;
import yap.sql.MySQLAccessor;
import yap.utils.TemplateConstants;

/**
 * Servlet implementation class BusinessServlet
 */
@WebServlet("/reviews" )
public class ReviewServlet extends HttpServlet {
	private String getReviewPageForSearchQuery(
			String query,
			ArrayList<YapReview> reviews,
			PaginationInfo pg) {
		STGroup templates = new STRawGroupDir("WebContent/Templates", '$', '$');

		ST body = templates.getInstanceOf(TemplateConstants.SEARCH_REVIEW_PAGE);
		body.add("prefilled_query", query);
		body.add("reviews", reviews);
		body.add("has_prev", pg.prevPageNumber >= 0);
		body.add("has_next", pg.nextPageNumber >= 0);
		body.add("page_info", pg);

		ST businessReviewListPage = templates.getInstanceOf(TemplateConstants.FULL_PAGE);
		businessReviewListPage.add(TemplateConstants.TITLE, "..::Yap::Reviews..");
		businessReviewListPage.add(TemplateConstants.BODY, body.render());

		return businessReviewListPage.render();
	}
	private String getReviewPageForBusiness(
			YapBusiness biz,
			ArrayList<YapReview> reviews,
			PaginationInfo pg) {
		STGroup templates = new STRawGroupDir("WebContent/Templates", '$', '$');

		ST businessHeader = templates.getInstanceOf(TemplateConstants.BUSINESS_HEADER_PAGE);
		businessHeader.add("business", biz);

		ST body = templates.getInstanceOf(TemplateConstants.BUSINESS_REVIEW_PAGE);
		body.add(TemplateConstants.BUSINESS_HEADER_PAGE, businessHeader.render());
		body.add("reviews", reviews);
		body.add("has_prev", pg.prevPageNumber >= 0);
		body.add("has_next", pg.nextPageNumber >= 0);
		body.add("page_info", pg);
		body.add("bizid", biz.getBusinessID());

		ST businessReviewListPage = templates.getInstanceOf(TemplateConstants.FULL_PAGE);
		businessReviewListPage.add(TemplateConstants.TITLE, "..::Yap::Reviews..");
		businessReviewListPage.add(TemplateConstants.BODY, body.render());

		return businessReviewListPage.render();
	}

	private String getErrorPageForMissingBusiness(String businessId) {
		STGroup templates = new STRawGroupDir("WebContent/Templates", '$', '$');

		ST body = templates.getInstanceOf(TemplateConstants.ERROR_LINE);
		body.add(TemplateConstants.ERROR_TEXT,
				"Could not find business with ID: '" + businessId + "'");

		ST businessReviewListPage = templates.getInstanceOf(TemplateConstants.FULL_PAGE);
		businessReviewListPage.add(TemplateConstants.TITLE, "..::Yap::AddReview..");
		businessReviewListPage.add(TemplateConstants.BODY, body.render());

		return businessReviewListPage.render();
	}

	private Integer getPageNumberFromRequest(HttpServletRequest request) {
		Integer pageNumber = 0;

		try {
			pageNumber = Integer.parseInt(request.getParameter("page"));
		} catch (NumberFormatException ne) {}  // Nothing to do, pageNumber = 0.

		return Math.max(pageNumber, 0); // Sanitize, current page-number should never be negative.
	}

	private static class PaginationInfo {
		public int nextPageNumber;
		public int prevPageNumber;
		public String nextPageUrl;
		public String prevPageUrl;

		private PaginationInfo() {
			nextPageNumber = prevPageNumber = -1;
			nextPageUrl = prevPageUrl = "";
		}

		public static PaginationInfo getNeighborPageInfo(
				int pageNumber,
				int reviewPerPage,
				int totalReview,
				String baseUrl) {
			PaginationInfo pg = new PaginationInfo();
			int endOfPageReviewIndex = pageNumber * reviewPerPage + reviewPerPage;

			if (endOfPageReviewIndex < totalReview) {
				pg.nextPageNumber = pageNumber + 1;
				pg.nextPageUrl = baseUrl + "&page=" + pg.nextPageNumber;
			}

			if (pageNumber > 0) {
				pg.prevPageNumber = pageNumber - 1;
				pg.prevPageUrl = baseUrl + "&page=" + pg.prevPageNumber;
			}

			return pg;
		}
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);

		Integer pageNumber = getPageNumberFromRequest(request);
		String businessId = request.getParameter("businessID");
		String query = request.getParameter("query");
		if (businessId != null) {
			YapBusiness b = YapBusiness.getBusinessWithBusinessId(businessId);
			if (b == null) {
				response.getWriter().print(getErrorPageForMissingBusiness(businessId));
				return;
			}
			Integer totalReview = YapReview.getReviewCountForBusiness(businessId);

			ArrayList<YapReview> reviews = YapReview.getReviewsForBusiness(
					businessId,
					pageNumber * YapReview.REVIEW_PER_PAGE);
			response.getWriter().print(
					getReviewPageForBusiness(
							b, reviews,
							PaginationInfo.getNeighborPageInfo(
									pageNumber,
									YapReview.REVIEW_PER_PAGE,
									totalReview,
									"reviews?businessID=" + businessId)));
		} else if (query != null) {
			Integer totalReview = YapReview.getReviewCountForQuery(query);

			ArrayList<YapReview> reviews = YapReview.getReviewsForQuery(
					query,
					pageNumber * YapReview.REVIEW_PER_PAGE);

			response.getWriter().print(
					getReviewPageForSearchQuery(
							query,
							reviews,
							PaginationInfo.getNeighborPageInfo(
									pageNumber,
									YapReview.REVIEW_PER_PAGE,
									totalReview,
									"reviews?query=" + query)));
		} else {
			response.getWriter().print(getErrorPageForMissingBusiness(businessId));
			return;
		}
	}
}
