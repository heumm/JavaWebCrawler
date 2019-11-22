package com.crawler.project.model;


public class HotelsVO {
	private String hotelName = "";
	private String hotelGrade = "";
	private String hotelLocation = "";
	private String hotelPrice = "";
	private String hotelPriceDiscounted = "";
	
	
	public String getHotelName() {
		return hotelName;
	}
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	public String getHotelGrade() {
		return hotelGrade;
	}
	public void setHotelGrade(String hotelGrade) {
		this.hotelGrade = hotelGrade;
	}
	public String getHotelLocation() {
		return hotelLocation;
	}
	public void setHotelLocation(String hotelLocation) {
		this.hotelLocation = hotelLocation;
	}
	public String getHotelPrice() {
		return hotelPrice;
	}
	public void setHotelPrice(String hotelPrice) {
		this.hotelPrice = hotelPrice;
	}
	public String getHotelPriceDiscounted() {
		return hotelPriceDiscounted;
	}
	public void setHotelPriceDiscounted(String hotelPriceDiscounted) {
		this.hotelPriceDiscounted = hotelPriceDiscounted;
	}
	
	
	@Override
	public String toString() {
		return "HotelsVO [호텔명=" + hotelName + ", 호텔등급=" + hotelGrade + ", 호텔위치=" + hotelLocation
				+ ", 호텔요금=" + hotelPrice + ", 할인요금=" + hotelPriceDiscounted + "]";
	}
	
}
