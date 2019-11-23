package com.crawler.project.model;

import java.sql.SQLException;
import java.util.List;

public interface HotelsDAO {
	public static final String DB_NAME = "crawler";	
	public static final String DB_USER = "hanheum";
	public static final String DB_PW = "1234";
	public static final String DB_TABLE_NAME_INFO = "com_hotels";
	public static final String DB_TABLE_NAME_REVIEW = "com_hotels_review";
	public static final String DB_URL = "jdbc:mysql://localhost:3307/" + DB_NAME + "?useSSL=false&useUnicode=true";
	
	
	
	
	
	public void insertInfo(HotelsVO info) throws SQLException;
	
	public void insertReview(List<HotelsReviewVO> review);

	public void deleteReview(String hotelName);
	
	public boolean isDuplicateKey(String tableName, String hotelName);
	
	public boolean isInfoExist(String hotelName);
	
}
