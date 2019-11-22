package com.crawler.project.model;

public class HotelsReviewVO {
	private String hotelName;
	private int reviewNum;
	private double reviewRate;
	private String reviewContent;
	private String reviewDate;
	
	
	public String getHotelName() {
		return hotelName;
	}
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	public int getReviewNum() {
		return reviewNum;
	}
	public void setReviewNum(int reviewNum) {
		this.reviewNum = reviewNum;
	}
	public double getReviewRate() {
		return reviewRate;
	}
	public void setReviewRate(double reviewRate) {
		this.reviewRate = reviewRate;
	}
	public String getReviewContent() {
		return reviewContent;
	}
	public void setReviewContent(String reviewContent) {
		if(reviewContent.equals("")) {
			this.reviewContent = "NULL";
		} else {
		this.reviewContent = reviewContent;
		}
	}
	public String getReviewDate() {
		return reviewDate;
	}
	public void setReviewDate(String reviewDate) {
		this.reviewDate = reviewDate;
	}
	
	
	@Override
	public String toString() {
		return "HotelsReviewVO [호텔명=" + hotelName + ", 리뷰 번호=" + reviewNum + ", 리뷰 평점=" + reviewRate
				+ ", 리뷰 내용=" + reviewContent + ", 리뷰 날짜=" + reviewDate + "]";
	}
	
	
	
	
//	hotel_name varchar(50) NOT NULL, 
//    review_num int unsigned NOT NULL, 
//    review_rate float unsigned, 
//    review_content varchar(200), 
//    review_date date, 
//    PRIMARY KEY (hotel_name, review_num) , 
//    FOREIGN KEY (hotel_name) REFERENCES com_hotels (hotel_name)
}
