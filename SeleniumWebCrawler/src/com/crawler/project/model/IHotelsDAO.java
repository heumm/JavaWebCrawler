package com.crawler.project.model;

import java.sql.SQLException;
import java.util.List;

public interface IHotelsDAO {
	public static final String DB_NAME = "crawler_db";	
	public static final String DB_USER = "crawleruser";
	public static final String DB_PW = "crawler";
	public static final String DB_TABLE_NAME_INFO = "com_hotels_2";
	public static final String DB_TABLE_NAME_REVIEW = "com_hotels_review_2";
	public static final String DB_URL = "jdbc:mysql://localhost:3306/" + DB_NAME + "?useSSL=false&useUnicode=true";
	
	
	
	
	
	public void insertInfo(HotelsVO info) throws SQLException;
	
	public void insertReview(List<HotelsReviewVO> review);

	public void deleteReview(String hotelName);
	
	public boolean isReviewDuplicated(String hotelName);
	
	public boolean isInfoExist(String hotelName);
	
}
