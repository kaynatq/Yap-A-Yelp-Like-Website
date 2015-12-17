package yap.data;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import yap.sql.MySQLAccessor;

public class YapBusiness {

	private String businessID;
	private String name;
	private String city;
	private String state;
	private String neighborhoods;
	private Double latitude;
	private Double longitude;
	private Double rating;

	/**
	 * Default Constructor
	 */
	public YapBusiness() {
		businessID = null;
		name = null;
		city = null;
		state = null;
		neighborhoods = null;
		latitude = 0.0;
		longitude = 0.0;
	}

	public String getBusinessID() {
		return businessID;
	}

	public void setBusinessID(String businessID) {
		this.businessID = businessID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getNeighborhoods() {
		return neighborhoods;
	}

	public void setNeighborhoods(String neighborhoods) {
		this.neighborhoods = neighborhoods;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		DecimalFormat twoDForm = new DecimalFormat("#.#");
		this.rating = Double.valueOf(twoDForm.format(rating));
	}

	public static YapBusiness getBusinessWithBusinessId(String businessId) {
		YapBusiness b = null;

		MySQLAccessor sqlA = new MySQLAccessor();
		sqlA.InvokeParametrizedQuery("SELECT * from Business WHERE businessID=?", businessId);

		while (sqlA.Next()) {
			b = new YapBusiness();
			b.setBusinessID(businessId);
			b.setName(sqlA.getString("name"));
			b.setCity(sqlA.getString("city"));
			b.setState(sqlA.getString("state"));
			b.setLatitude(sqlA.getDouble("latitude"));
			b.setLongitude(sqlA.getDouble("longitude"));
			b.setNeighborhoods(sqlA.getString("neighborhoods"));

			b.setRating(YapReview.getRatingForBusiness(businessId));

			break;
		}

		sqlA.Close();

		return b;
	}

	public static ArrayList<YapBusiness> getBusinesses(String sortby) {
		ArrayList<YapBusiness> businesses = new ArrayList<>();

		String query = "SELECT * FROM Business";
		if (sortby != null) {
			query += " ORDER BY " + sortby;
		}

		MySQLAccessor sqlAccessor = new MySQLAccessor();
		sqlAccessor.InvokeQuery(query);
		while (sqlAccessor.Next()) {
			YapBusiness business = new YapBusiness();
			business.setBusinessID(sqlAccessor.getString("businessID"));
			business.setName(sqlAccessor.getString("name"));
			business.setCity(sqlAccessor.getString("city"));
			business.setState(sqlAccessor.getString("state"));
			business.setNeighborhoods((sqlAccessor.getString("neighborhoods")));
			business.setLongitude((sqlAccessor.getDouble("longitude")));
			business.setLatitude(sqlAccessor.getDouble("latitude"));
			businesses.add(business);
		}
		sqlAccessor.Close();

		return businesses;
	}

	public boolean InsertToDB() {
		MySQLAccessor a = new MySQLAccessor();
		HashMap<String, Object> vm = new HashMap<>();
		
		vm.put("name", this.name);
		vm.put("city", this.city);
		vm.put("state", this.state);
		vm.put("latitude", this.latitude);
		vm.put("longitude", this.longitude);
		vm.put("neighborhoods", this.neighborhoods);
		
		return a.InsertIntoTable("Business", vm) == 1;
	}

}
