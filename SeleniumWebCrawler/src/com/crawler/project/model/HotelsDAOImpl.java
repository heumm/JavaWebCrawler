package com.crawler.project.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class HotelsDAOImpl implements HotelsDAO{

	private static HotelsDAO dao = new HotelsDAOImpl();
	private Connection connection;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	
	
	
	public HotelsDAOImpl() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PW);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static HotelsDAOImpl getInstance() {
		if(dao == null) dao = new HotelsDAOImpl();
		return (HotelsDAOImpl)dao;
	}

	@Override
	public void insertInfo(HotelsVO info) {
		String sql = "INSERT INTO " + DB_TABLE_NAME_INFO + " "
				+ "(hotel_name, hotel_grade, hotel_location, hotel_price, hotel_price_discounted) "
				+ "VALUES (?, ?, ?, ?, ?)";
		
		try {
//			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PW);
			
			pstmt = connection.prepareStatement(sql);

			pstmt.setString(1, info.getHotelName());
			pstmt.setString(2, info.getHotelGrade());
			pstmt.setString(3, info.getHotelLocation());
			pstmt.setString(4, info.getHotelPrice());
			pstmt.setString(5, info.getHotelPriceDiscounted());
			
			int rn = pstmt.executeUpdate();
			if(rn == 1) {
				System.out.println("등록 성공!");
			} else {
				System.out.println("등록 실패!");
			}

		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
		
			//	         connection.close();
			//	         pstmt.close();
		}
		
		
	}

	@Override
	public void insertReview(List<HotelsReviewVO> reviews) {
		String sql = "INSERT INTO " + DB_TABLE_NAME_REVIEW + " "
				+ "(hotel_name, review_num, review_rate, review_content, review_date) "
				+ "VALUES (?, ?, ?, ?, ?)";
		int rn = 0;
		try {
//			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PW);
			pstmt = connection.prepareStatement(sql);
			
			for(HotelsReviewVO review : reviews) {
				pstmt.setString(1, review.getHotelName());
				pstmt.setInt(2, review.getReviewNum());
				pstmt.setDouble(3, review.getReviewRate());
				pstmt.setString(4, review.getReviewContent());
				pstmt.setString(5, review.getReviewDate());
			
				rn += pstmt.executeUpdate();
			}
			System.out.println(rn + "개의 행이 삽입되었습니다.");
		} catch(SQLException e){
			e.printStackTrace();
		} finally {
		
			//	         connection.close();
			//	         pstmt.close();
		}
		
	}
	
	@Override
	public void deleteReview(String hotelName) {
		String sql = "DELETE FROM " + DB_TABLE_NAME_REVIEW + " "
				+ "WHERE hotel_name=?";

		try {
			//	         connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PW);
			pstmt = connection.prepareStatement(sql);

			pstmt.setString(1, hotelName);

			int rn = pstmt.executeUpdate();
			if(rn != 0) {
				System.out.println(rn + " 개의 행 삭제됨.");
			} else {
				System.out.println("삭제 실패!");
			}

		} catch(SQLException e){
			e.printStackTrace();
		} finally {
		
			//	         connection.close();
			//	         pstmt.close();
		}

	}
	
	@Override
	public boolean isDuplicateKey(String tableName, String hotelName) {
		String sql = "SELECT * FROM " + tableName + " "
				+ "WHERE hotel_name=?";

		try {
			//	         connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PW);
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, hotelName);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				System.err.print(hotelName);
				System.out.println(" 의 데이터는 이미 테이블에 존재합니다.");
				return true;
			}
		} catch(SQLException e){
			e.printStackTrace();
		} finally {
		
			//	         connection.close();
			//	         pstmt.close();
		}
		return false;
	}
	
	@Override
	public boolean isInfoExist(String hotelName) {
		String sql = "SELECT * FROM " + DB_TABLE_NAME_INFO + " "
				+ "WHERE hotel_name=?";

		try {
			//	         connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PW);
			pstmt = connection.prepareStatement(sql);

			pstmt.setString(1, hotelName);
			rs = pstmt.executeQuery();
			//rs.next()값이 true라면 삽입가능 false라면 삽입불가능
			if(rs.next()) {
				return true;
			} else {
				System.err.print(hotelName);
				System.out.println(" 의 정보가 존재하지 않아 넘어갑니다.");
			}
		} catch(SQLException e){
			e.printStackTrace();
		} finally {
		
			//	         connection.close();
			//	         pstmt.close();
		}
		return false;
	}
	
	
	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection conn) {
		this.connection = conn;
	}

	public PreparedStatement getPstmt() {
		return pstmt;
	}

	public void setPstmt(PreparedStatement pstmt) {
		this.pstmt = pstmt;
	}

	public ResultSet getRs() {
		return rs;
	}

	public void setRs(ResultSet rs) {
		this.rs = rs;
	}

	

	

}
