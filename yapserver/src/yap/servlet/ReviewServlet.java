package yap.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import yap.data.YapReview;

/**
 * Servlet implementation class BusinessServlet
 */
@WebServlet("/reviews" )
public class ReviewServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		
		YapReview yr = new YapReview();
		ArrayList<Integer> reviewIDs = yr.getReviewIDs(request.getParameter("businessID"));
		ArrayList<String> titleAndBody = yr.viewAllReviews(reviewIDs, request.getParameter("businessID"));
		
		response.getWriter().println(ServletUtils.getHtmlForTitleAndBody(titleAndBody.get(0)
				, titleAndBody.get(1)));
	}
}
