package yap.data;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import yap.sql.MySQLAccessor;

public class YapReview {
	public static int REVIEW_PER_PAGE = 2;
	
	private int reviewId;
	private Double rating;
	private Date reviewDate;
	private String userId;
	private String userName;
	private String text;
	private String businessId;
	
	public YapReview() {}
	
	public int getReviewId() {
		return reviewId;
	}

	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		this.rating = Double.valueOf(twoDForm.format(rating));
	}

	public String getReviewDate() {
		DateFormat df = new SimpleDateFormat("MMM dd, yyyy");
		return df.format(reviewDate);
	}

	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	
	public static ArrayList<YapReview> getReviewsForBusiness(String businessID, Integer start) {
		ArrayList<YapReview> reviews = new ArrayList<>();
		
		MySQLAccessor sqlAccessor = new MySQLAccessor();
		sqlAccessor.InvokeParametrizedQuery(
				"SELECT * FROM Review WHERE businessID=? LIMIT " + start + "," + YapReview.REVIEW_PER_PAGE,
				businessID);
		
		while (sqlAccessor.Next()) {
			YapReview r = new YapReview();
			r.setUserId(sqlAccessor.getString("userID"));
			r.setUserName(YapUser.getUserNameWithUserId(r.getUserId()));
			r.setRating(sqlAccessor.getDouble("rating"));
			r.setReviewDate(sqlAccessor.getDate("date"));
			r.setText(sqlAccessor.getString("text"));
			r.setBusinessId(sqlAccessor.getString("businessID"));
			
			reviews.add(r);
		}	

		sqlAccessor.Close();
		return reviews;
	}
	
	public static Double getRatingForBusiness(String businessID) {
		MySQLAccessor sqlAccessor = new MySQLAccessor();
		double total = 0.0;
		int count = 0;
		
		sqlAccessor.InvokeParametrizedQuery(
				"SELECT rating FROM Review WHERE businessID=?",
				businessID);
		while (sqlAccessor.Next()) {
			count++;
			total += sqlAccessor.getDouble("rating");
		}
		sqlAccessor.Close();
		
		return count == 0 ? 0.0 : total / count;
	}
	
	public static int getReviewCountForBusiness(String businessId) {
		return MySQLAccessor.getCount(
				"SELECT count(*) as total from Review where businessID=?",
				businessId,
				"total");
	}
	
	public static int getReviewCountForQuery(String query) {
		return MySQLAccessor.getCount(
				"SELECT count(*) as total from Review where text LIKE ?",
				"%" + query + "%",
				"total");
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public static ArrayList<YapReview> getReviewsForQuery(String query, int start) {
		ArrayList<YapReview> reviews = new ArrayList<>();
		
		MySQLAccessor sqlAccessor = new MySQLAccessor();
		sqlAccessor.InvokeParametrizedQuery(
				"SELECT * FROM Review WHERE text LIKE ? LIMIT " + start + "," + YapReview.REVIEW_PER_PAGE,
				"%" + query + "%");
		
		while (sqlAccessor.Next()) {
			YapReview r = new YapReview();
			r.setUserId(sqlAccessor.getString("userID"));
			r.setUserName(YapUser.getUserNameWithUserId(r.getUserId()));
			r.setRating(sqlAccessor.getDouble("rating"));
			r.setReviewDate(sqlAccessor.getDate("date"));
			r.setText(sqlAccessor.getString("text"));
			r.setBusinessId(sqlAccessor.getString("businessID"));
			
			reviews.add(r);
		}	

		sqlAccessor.Close();
		return reviews;
	}
}