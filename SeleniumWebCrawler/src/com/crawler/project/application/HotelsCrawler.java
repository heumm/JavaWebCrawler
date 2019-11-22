package com.crawler.project.application;


import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.swing.JCheckBox;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.crawler.project.model.HotelsDAO;
import com.crawler.project.model.HotelsReviewVO;
import com.crawler.project.model.HotelsVO;
import com.crawler.project.model.IHotelsDAO;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class HotelsCrawler extends Crawler implements CrawlerInterface{

	private String baseUrl;
	private WebElement wElement = null;
	private List<WebElement> wElements = null;
	private String url = "";
	private String src = "";
	private Document doc = null;
	private List<HotelsReviewVO> vos = new ArrayList<>();
	private HotelsDAO dao = HotelsDAO.getInstance();
	
	
	
	
	private WebDriverWait wait = new WebDriverWait(driver, 10);

	public HotelsCrawler() {
		super();
		this.baseUrl = "https://kr.hotels.com/";
		driver.get(baseUrl);   //초기페이지 접속.
	}


	@Override
	public void crawlInfo() {
		try {
			if(dao.getConnection().isClosed()) dao.setConnection(DriverManager.getConnection(IHotelsDAO.DB_URL, IHotelsDAO.DB_USER, IHotelsDAO.DB_PW));
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		src = driver.getPageSource();
		doc = Jsoup.parse(src); //url에 해당되는 페이지 소스를 가져온다.
		String temp = ""; //스크롤을 더이상 내리지 못할 경우에 종료하도록 임시 String을 저장


		int i = 1;
		List<String> newTab = new ArrayList<>();
		HotelsVO vo = new HotelsVO();
		while(true) {
			try {
				src = driver.getPageSource();

				//            System.out.println(i);
//				rWait();
				wElement = driver.findElement(By.xpath("//*[@id=\"listings\"]/ol/li[" + i + "]/article/section/div/h3/a"));
				//            System.out.println(wElement);
				wElement.click();   // 호텔 클릭


				
				newTab.addAll(driver.getWindowHandles());
//				rWait();
				driver.switchTo().window(newTab.get(1));
				src = driver.getPageSource();
				doc = Jsoup.parse(src);


				//            -------------------------------                  
				//system.out.println(src); // 현재 html 소스 출력
				//아래 한 줄은 호텔명을 검색하게 해서 나오지 않는다면 다른 창이 띄워져있을 가능성이 높기 때문에 바로 예외 처리 구문으로 넘겨줍니다.
				driver.findElement(By.cssSelector("#property-header > div.property-description > div.vcard > h1"));

				//            -------------------------------   

				Elements hotelName = doc.select("#property-header > div > div.vcard > h1");
				
				//            printElements("호텔명 : ", hotelName);
				System.out.println("호텔명 : " + hotelName.text());
				//            star-rating-text-strong. 호텔등급4,5성에만 해당되기때문에 제외시킴
				vo.setHotelName(hotelName.text());
				Elements hotelGrade = doc.select("#property-header > div > div.vcard > span.star-rating-text.widget-star-rating-overlay.widget-tooltip.widget-tooltip-responsive.widget-tooltip-ignore-touch");
				
				
				
				vo.setHotelGrade(hotelGrade.text().substring(0, hotelGrade.text().indexOf("급")));
				System.out.println("호텔 등급 : " + hotelGrade.text().substring(0, hotelGrade.text().indexOf("급")));

				Elements hotelLocation = doc.select("#property-header > div > span > span");
				vo.setHotelLocation(hotelLocation.text());
				printElements("호텔 위치 : ", hotelLocation);

				//            -------------------------------               
				// 요금(숫자)과 함께 -> 줄이 그어진 요금은 이 숙박 시설에서 결정하여 제공하는 표준 요금입니다. 라고 출력되기때문에 if문으로 변경함

				Elements hotelPrices = doc.select("#book-info-container > div:nth-child(1) > div > div.pricing > div > del");//일반 요금을 브라우저에서 스크랩
				String strHotelPrices = "";

				if(hotelPrices.text().contains("줄이 그어진 요금은 이 숙박 시설에서 결정하여 제공하는 표준 요금입니다.")) {//객실 요금이 할인이 된 경우

					strHotelPrices = hotelPrices.text();
					//               vo.setHotelPrice(Integer.parseInt(hotelPrices.text().split(" 줄이 그어진 요금은 이 숙박 시설에서 결정하여 제공하는 표준 요금입니다.")[0].substring(hotelPrices.text().indexOf("₩")+1, hotelPrices.text().indexOf(" ")).replace(",", "").replace(" ", "")));//vo객체 객실요금 설정
					//               System.out.println("표준 요금 : " + hotelPrices.text().split(" 줄이 그어진 요금은 이 숙박 시설에서 결정하여 제공하는 표준 요금입니다.")[0].substring(hotelPrices.text().indexOf("₩")+1, hotelPrices.text().indexOf(" ")).replace(",", "").replace(" ", ""));//객실요금 콘솔출력
					vo.setHotelPrice(strHotelPrices.split(" 줄이 그어진 요금은 이 숙박 시설에서 결정하여 제공하는 표준 요금입니다.")[0].trim());//vo객체 객실요금 설정
					System.out.println("표준 요금 : " + strHotelPrices.split(" 줄이 그어진 요금은 이 숙박 시설에서 결정하여 제공하는 표준 요금입니다.")[0].trim());//객실요금 콘솔출력
					Elements hotelPricesDiscounted = doc.select("#book-info-container > div:nth-child(1) > div > div.pricing > div > span.current-price.has-old-price");//할인된 요금을 브라우저에서 스크랩
					String strHotelPricesDiscounted = hotelPricesDiscounted.text() + " ";
					vo.setHotelPriceDiscounted(strHotelPricesDiscounted.trim());//vo객체 할인요금 설정
					System.out.println("할인 요금 : " + strHotelPricesDiscounted.trim());//할인요금 콘솔출력
				} else {
					//               driver.findElement(By.cssSelector("#book-info-container > div:nth-child(1) > div.featured-price > div > div > span"));
					//금액이 없을경우 예외발생을 시키기 위해 한줄 추가해놨음

					hotelPrices = doc.select("#book-info-container > div:nth-child(1) > div.featured-price > div > div > span");
					strHotelPrices = hotelPrices.text() + " ";
					vo.setHotelPrice(strHotelPrices.trim());//vo객체 객실요금 설정

					System.out.println("표준 요금 : " + strHotelPrices.trim());
					vo.setHotelPriceDiscounted(strHotelPrices.trim());
					//               vo.setHotelPriceDiscounted(Integer.parseInt(hotelPrices.text().split(" 줄이 그어진 요금은 이 숙박 시설에서 결정하여 제공하는 표준 요금입니다.")[0].replace("₩", "").replace(",", ""))); 
				}

				dao.insertInfo(vo);//여기서 예외발생

				driver.close();
//				rWait();
				driver.switchTo().window(newTab.get(0));
				newTab.removeAll(newTab);
			} catch(NoSuchElementException e) {
				if(!newTab.isEmpty()) {   //열려있는 탭이 있다면(다른 창이 띄워져서 예외가 발생했다면) 창을 닫아주는 조건문
//					e.printStackTrace();
					System.err.println("금액이 없거나 다른 창이 띄워졌습니다.");
					driver.close();
					driver.switchTo().window(newTab.get(0));
					newTab.removeAll(newTab);

					//               try {
					//                  dao.insertInfo(vo);
					//               } catch (SQLException e1) {
					//                  e1.printStackTrace();
					//               }

				} else if(temp.equals(src)) {
					System.err.println("완료. 종료합니다.");
					try {
						dao.getConnection().close();
						dao.getPstmt().close();
					} catch(SQLException sqle) {
							
					}
					break;
//					driver.quit();
//					System.exit(0);
				} else {
					System.err.println("NoSuchElement");
					jse.executeScript("window.scrollTo(0, document.querySelector('#listings').scrollHeight)");
					temp = driver.getPageSource();
				}
			}
			//         catch(InterruptedException ie) {
			//            // ie.printStackTrace(); -> printStackTrace를 출력하지 않음(주석처리) 콘솔용량부족현상
			//         } 
			catch(SQLException sqle) {
				if(sqle instanceof MySQLIntegrityConstraintViolationException) {
					System.err.println("Duplicate PRIMARY KEY ");
				}
				sqle.printStackTrace();
				driver.close();
				driver.switchTo().window(newTab.get(0));
				newTab.removeAll(newTab);
			} catch(Exception e) {
				//e.printStackTrace();  -> printStackTrace를 출력하지 않음(주석처리) 콘솔용량부족현상
			}
			i++;
			//         System.out.println(i);
		}
	}   //crawlInfo() method end

	
	
	
	@Override
	public void crawlReview() {
		try {
			if(dao.getConnection().isClosed()) dao.setConnection(DriverManager.getConnection(IHotelsDAO.DB_URL, IHotelsDAO.DB_USER, IHotelsDAO.DB_PW));
			
			scrollDownToCheck("#result-info-container > div"); // 19.10.19 update

			src = driver.getPageSource();   //크롬이 현재 열고 있는 페이지의 소스를 src 문자열에 저장
			doc = Jsoup.parse(src);      //페이지소스가 저장된 문자열을 파싱해서 Document객체에 저장 

			Elements totalHotels = doc.select("div.filters-summary"); 
			printElements("전체 호텔 수", totalHotels);
			Elements element = doc.select("div.reviews-box.resp-module").select("a");

			
			for (Element e : element) {	//리뷰페이지url 순차적으로 접근
				

				url = "https://kr.hotels.com" + e.attr("href");
				driver.get(url);
				src = driver.getPageSource();
				doc = Jsoup.parse(src);

				Elements totalRev = doc.select("#guest-reviews > div.reviews-content.ugc-redesign.loading > "
						+ "div.reviews-tab-content.brand-reviews.active > "
						+ "div.brand-reviews-content.clearfix > div.brand-reviews-filter > div > p > span");
				System.out.println("전체 리뷰 수 : " + totalRev);
				//            int totalRevInt = Integer.parseInt(totalRev.text().replace(",", ""));
				

				Elements nameHotels = doc.select("h2.widget-overlay-hd > span");
				printElements("nameHotels = ", nameHotels);//필수
				// 리뷰테이블에 이미 해당 호텔데이터가 존재하거나 인포 테이블에 존재하지 않거나
				if(dao.isReviewDuplicated(nameHotels.text()) || !(dao.isInfoExist(nameHotels.text()))) { continue; }
				// 인포 존재 여부 체크. 조건식에 추가해야함. 
				// 호텔명이 인포 테이블에 존재하지 않다면 데이터가 들어가지 않기 때문에 검사해야한다.
				
				int totalRevInt = 0;
				//다음 버튼이 존재하지 않을 때 까지 다음버튼을 누르고 정보를 가져온다.
				while(true) {	//리뷰 페이지 넘기기 위한 반복
					try {
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("#guest-reviews > div.review-tabs > div > a.tab-button.active.processing")));
					} catch (TimeoutException e2) {
						System.out.println("248.Timeout발생");
						driver.navigate().refresh();
						continue;
					}
					
					src = driver.getPageSource();
					doc = Jsoup.parse(src);

					try {
						wElement = driver.findElement(By.cssSelector("#guest-reviews > div.reviews-content.ugc-redesign.loading > div.reviews-tab-content.brand-reviews.active > div.brand-reviews-content.clearfix > div.brand-reviews-listing > div.pagination > div.pagination-controls > a.cta.cta-secondary.cta-next"));
					} catch(WebDriverException wde) {
						if(wde instanceof NoSuchElementException) {
							Elements reviewBox = doc.select("div.brand-reviews-listing").select("div.review-card"); //한 페이지 내 리뷰 상자들
							for (Element el : reviewBox) {
								HotelsReviewVO vo = new HotelsReviewVO();
								Elements revRating = el.select("span.rating-score");   //각각의 리뷰에 해당하는 평점 하나
								Elements revContent = el.select("blockquote.expandable-content.description");   //각각의 리뷰
								Elements revDate = el.select("span.date");   //각각의 리뷰에 해당하는 작성 날짜
								String strDate = revDate.text().split("작성 날짜: ")[1];
								String dateYY = strDate.split("년")[0];
								String dateMM = strDate.split("년")[1].split("월")[0];
								String dateDD = strDate.split("월")[1].split("일")[0];
								vo.setHotelName(nameHotels.text());
								vo.setReviewNum(++totalRevInt);
								vo.setReviewRate(Double.parseDouble(revRating.text()));   //vo.setrating
								vo.setReviewContent(revContent.text());   //vo.setcontent
								vo.setReviewDate(dateYY+dateMM+dateDD);   //vo.setdate
								vos.add(vo);
//								System.out.println(vo);
							}

							System.out.println("nosuchelement 마지막 페이지");
//							for (HotelsReviewVO vo : vos) {
//								System.out.println(vo);
//							}
							dao.insertReview(vos);	//DB삽입
							System.out.println(totalRevInt + "개의 리뷰");
							vos.clear();	//리스트 클리어
							break;

						} else {
							wde.printStackTrace();
						}
					}

					Elements reviewBox = doc.select("div.brand-reviews-listing").select("div.review-card"); //한 페이지 내 리뷰 상자들
					for (Element el : reviewBox) {
						HotelsReviewVO vo = new HotelsReviewVO();
						Elements revRating = el.select("span.rating-score");   //각각의 리뷰에 해당하는 평점 하나
						Elements revContent = el.select("blockquote.expandable-content.description");   //각각의 리뷰
						Elements revDate = el.select("span.date");   //각각의 리뷰에 해당하는 작성 날짜
						String strDate = revDate.text().split("작성 날짜: ")[1];
						String dateYY = strDate.split("년")[0];
						String dateMM = strDate.split("년")[1].split("월")[0];
						String dateDD = strDate.split("월")[1].split("일")[0];
						vo.setHotelName(nameHotels.text());
						vo.setReviewNum(++totalRevInt);
						vo.setReviewRate(Double.parseDouble(revRating.text()));   //vo.setrating
						vo.setReviewContent(revContent.text());   //vo.setcontent
						vo.setReviewDate(dateYY+dateMM+dateDD);   //vo.setdate
						vos.add(vo);
//						System.out.println(vo);
					}
					try {

						wElement.sendKeys(Keys.ENTER);
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("#guest-reviews > div.review-tabs > div > a.tab-button.active.processing")));
						System.out.println(totalRevInt);
					} catch(WebDriverException wde) {
						if(wde instanceof ElementClickInterceptedException) {
							System.out.println("ElementClickIntercepted Exception");
							dao.deleteReview(nameHotels.text());
							break;
						} else if(wde instanceof TimeoutException) {
							System.out.println("TimeoutException");
							vos.clear();
							dao.deleteReview(nameHotels.text());
							break;
						}
						wde.printStackTrace();
					}
				} //while
			} //for

		} catch (Exception e) {
			e.printStackTrace();  // 자주 발생하지 않는 exception이기 때문에 출력하여 원인을 찾아내기 위함(19.10.21주석 해제)
		} finally {
//			driver.quit();
			try {
				dao.getConnection().close();
				dao.getPstmt().close();
			} catch(SQLException sqle) {
					
			}
		}

	} //crawl method




	//페이지에 현존하는 체크박스들을 map에 저장하는 메서드
	//체크박스가 있는곳을 페이지요소검사로 찾아내야함.

	@Override
	public void sendSearch(String input) throws Exception {   // 191021 검색창에 아무것도 입력하지 않을 경우 한국을 자동으로 입력하고 전송 버튼을 누르도록 수정
		if(input.equals("")) {
			driver.manage().timeouts().implicitlyWait(2000, TimeUnit.MILLISECONDS);
			driver.findElement(By.xpath("//*[@id=\"qf-0q-destination\"]")).sendKeys("한국");
//			rWait();
			src = driver.getPageSource();
			wElement = driver.findElement(By.cssSelector("body > div.widget-autosuggest.widget-autosuggest-visible"));
			wElements = wElement.findElements(By.cssSelector("tr"));
			for (WebElement we : wElements) {
				if(we.getText().equals("한국")) {
					we.click();
					driver.findElement(By.cssSelector("#hds-marquee > div.row.centered-wrapper.w-full.po-r > div.container-queryform.col-12.col-l4.mv-bird > div > form > div.widget-query-group.widget-query-ft > button")).submit();   //검색버튼 누르고 다음페이지 요청
					break;
				}
			}
		} else {
			driver.findElement(By.xpath("//*[@id=\"qf-0q-destination\"]")).sendKeys(input);   //지역에 서울, 한국을 입력
//			rWait();
			driver.findElement(By.cssSelector("#hds-marquee > div.row.centered-wrapper.w-full.po-r > div.container-queryform.col-12.col-l4.mv-bird > div > form > div.widget-query-group.widget-query-ft > button")).submit();   //검색버튼 누르고 다음페이지 요청
//			rWait();
		}
		driver.manage().timeouts().implicitlyWait(600, TimeUnit.MILLISECONDS);
	}

	@Override
	public void scrollDownToCheck(String cssSelector){
		driver.manage().timeouts().implicitlyWait(2000, TimeUnit.MILLISECONDS);
		while(true) {
			try {
				System.out.println(driver.findElement(By.cssSelector(cssSelector)).getText());
				// "선택하신 날짜에 추가로 18개 숙박 시설이 예약 불가입니다." 에 해당하는 메시지를 출력한다. (스크롤이 다 끝났다는 뜻)
				// 해당 메시지를 찾지 못한다면 예외 처리 부분으로 바로 넘어간다.
				break;
			} catch(Exception e) {
				if(e instanceof NoSuchElementException) { //   NoSuchElementException 발생 상황이라면 평소처럼 스크롤 수행
					jse.executeScript("window.scrollTo(0, document.querySelector('#listings').scrollHeight)");
					// 191014_update내용: id가 listings인 요소의 마지막 부분으로 스크롤하도록 변경
					continue;
				} else { // NoSuchElementException 발생 상황이 아닌 특수 예외가 발생한 경우 멈추고 오류 메시지를 출력시킴.
					e.printStackTrace();
					break;
				}
			}
		}
		driver.manage().timeouts().implicitlyWait(400, TimeUnit.MILLISECONDS);
	}


	
	

}   //class end


