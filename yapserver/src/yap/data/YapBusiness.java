package yap.data;

public class YapBusiness {

	private String businessID;
	private String name;
	private String city;
	private String state;
	private String neighborhoods;
	private double latitude;
	private double longitude;
	
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

}
