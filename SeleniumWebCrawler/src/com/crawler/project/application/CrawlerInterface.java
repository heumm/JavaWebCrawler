package com.crawler.project.application;

import java.util.Map;

import javax.swing.JCheckBox;

import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public interface CrawlerInterface {
	
	// chromedriver의 위치 지정 - 시스템변수(환경변수) 설정
    // ★ 웹드라이버 구현 -> 최초의 웹 브라우저 실행을 위한 세팅을 하기 위함 ★ // Crawler 클래스의 Constructor 지정
	// 해당 위치에서  System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH); 선언
	public static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
    public static final String WEB_DRIVER_PATH = "chromedriver/chromedriver77.exe";
	
    
    // 페이지 크롤링 메소드 (리뷰 선언)
	public void crawlReview();
	
	public void crawlInfo();
	
	// 스크롤을 끝까지 내려서 페이지를 갱신하는 메소드 (JavaScript 활용)
	// public void scrollDownToCheck(String src) throws Exception;
	public void scrollDownToCheck(String cssSelector); // 스크롤을 끝까지 내리는 메소드에서 selector 추가 업데이트 19.10.19 
	
	// Elements객체 안의 리스트를 모두 출력하는 메소드
	public void printElements(String label, Elements es);
	
	// 검색창에 검색어를 입력하게하는 메소드
	// GUI 전용 메소드 : 검색을 하게하는 화면구현 (검색창 - 서울, 한국)
	public void sendSearch(String input) throws Exception;
	
	
	


	
}
