package com.crawler.project.application;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

	// 인터페이스를 구현하기 위해서 아래와 같이 선언함 (Properties)
public abstract class Crawler{
	
    // 웹 드라이버는 웹 드라이버 선언
    protected WebDriver driver;
    
    // 자바스크립트를 동작할 수 있도록 하는 클래스
    // 자바스크립트 익스큐터는 페이지에 있는 스크립트에 메소드로 명령을 줄 수 있도록 함
	protected JavascriptExecutor jse;
	
	
//===========================================================	
	// 크롬드라이버 선언
	protected ChromeDriver cd;	// (1)	다형성을 기반으로 수정할 예정											
//===========================================================	
	
	// CrawlerInterface를 초기화시킴 
	// Why? -> 선언된 변수의 자리에 다른 값을 넣어야 하기 때문에 
	//	예를 들면, GUI 화면구현에서 호텔스닷컴을 선택했기 때문에 Crawler.inst = new HotelsReviewCrawler(); 가 온다.
	// 	반대로 아고다, 익스피디아, 트립어드바이저 등을 활용할 때 값이 달라지기 때문에 그에 맞는 변수를 지정할 수 있도록 값을 초기화 한다. (중요!!!)
	public static CrawlerInterface inst = null;
	
	
//===========================================================	
	// CrawlerInterface에서 시스템변수(환경변수) 설정한 이유.
	// Constructor
    public Crawler() { 
    	// System Property SetUp
    	// ★ 웹드라이버 구현 -> 최초의 웹 브라우저 실행을 위한 세팅 ★
        System.setProperty(CrawlerInterface.WEB_DRIVER_ID, CrawlerInterface.WEB_DRIVER_PATH);
        
        //Driver SetUp
        this.driver = new ChromeDriver();
        
        this.jse = (JavascriptExecutor)driver;
        driver.manage().timeouts().implicitlyWait(3000, TimeUnit.MILLISECONDS);
//        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
//        driver.manage().timeouts().setScriptTimeout(3, TimeUnit.SECONDS);
	}
//===========================================================    
    // 관례로, 클래스 내부의 인스턴스들을 접근권한에 대해 캡슐화 하는 작업
    // getters and setters

	
	
	//DB에 저장하기 위해 Elements에 저장된 요소들을 하나씩 관리하는 메소드
//	public WebDriver setDriver() {
//		return new ChromeDriver();
//	}
    
    public void crawlReview() {
		
	}
	
	public void crawlInfo() {
		
	}
	//최대500ms 까지 범위
	
	// rWait -> sleep을 랜덤값으로 변환
	

// 19.10.19 update (수정 전)	------------------------------------
/*
 	@Override
	public void scrollDownToCheck(String src) throws Exception {
		String temp = "";
		while(!src.equals(temp)) {
			temp = src;
			
			// 'selenium' 안에 있는 라이브러리 -> jse.executeScript(webDriver를 상속하고 있음)
			jse.executeScript("window.scrollTo(0, document.querySelector('#listings').scrollHeight)"); 
													// 191014_update내용: id가 listings인 요소의 마지막 부분으로 스크롤하도록 변경
			rWait(2000); // 스크롤 내리는 동안 딜레이 시간 설정 변경(1000 -> 2000)
			src = driver.getPageSource();
		}
	}
	
*/	
	
	// Elements객체 안의 리스트를 모두 출력하는 메소드
	// Elements가 들어있는 es를 더이상 값이 없을 때까지 하나씩 출력하는 메소드 
	public void printElements(String label, Elements es) {
		for (Element e : es) {
			System.out.println(label + e.text());
		}
	}
	
	// 검색창에 검색어를 입력하게하는 메소드
	// 추상화로 선언한 다음에 자식 클래스에서 GUI로 구현 가능함, 실제로는 실행결과창에 input으로 검색어 지정
	// Scanner 로도 구현 가능.
	
	


    
    
}
