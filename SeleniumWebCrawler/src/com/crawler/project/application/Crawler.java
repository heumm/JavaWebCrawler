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
	protected JavascriptExecutor jse;
	public static Crawlable instance = null;
	
	
    public Crawler() { 
    	// System Property SetUp
        System.setProperty(Crawlable.WEB_DRIVER_ID, Crawlable.WEB_DRIVER_PATH);
        
        //Driver SetUp
        this.driver = new ChromeDriver();
        this.jse = (JavascriptExecutor)driver;
        driver.manage().timeouts().implicitlyWait(3000, TimeUnit.MILLISECONDS);
//        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
//        driver.manage().timeouts().setScriptTimeout(3, TimeUnit.SECONDS);
	}
	
	// Elements를 모두 출력하는 메소드
	public void printElements(String label, Elements es) {
		for (Element e : es) {
			System.out.println(label + e.text());
		}
	}
    
    
}
