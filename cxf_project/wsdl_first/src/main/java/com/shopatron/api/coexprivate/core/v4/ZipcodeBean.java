package com.shopatron.api.coexprivate.core.v4;

public class ZipcodeBean {
	private String zip;
	private String city;
	private String state;
	private String lat;
	private String lon;
	private String timezone;
	private String dst;
	
	public String toString() {
		String separator = " | ";
		StringBuilder builder = new StringBuilder();
		
		builder.append("[");
		builder.append("zipcode: ").append(zip).append(separator);
		builder.append("city: ").append(city).append(separator);
		builder.append("state: ").append(state).append(separator);
		builder.append("latitude: ").append(lat).append(separator);
		builder.append("longitude: ").append(lon).append(separator);
		builder.append("timezone: ").append(timezone).append(separator);
		builder.append("dst: ").append(dst).append(separator);
		builder.append("]");
		
		return builder.toString();
	}
	
	public String getZip() {
		return zip;
	}
	
	public void setZip(String zip) {
		this.zip = zip;
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
	
	public String getLat() {
		return lat;
	}
	
	public void setLat(String lat) {
		this.lat = lat;
	}
	
	public String getLon() {
		return lon;
	}
	
	public void setLon(String lon) {
		this.lon = lon;
	}
	
	public String getTimezone() {
		return timezone;
	}
	
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
	
	public String getDst() {
		return dst;
	}
	
	public void setDst(String dst) {
		this.dst = dst;
	}
}
