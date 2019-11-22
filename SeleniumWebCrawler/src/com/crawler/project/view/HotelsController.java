package com.crawler.project.view;

import com.crawler.project.application.Crawler;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class HotelsController {

	@FXML
	private TextField search;
	@FXML
	private Button buttonInfo;
	@FXML
	private Button buttonReview;
	
	
	
	@FXML
	private void inputSearch() throws Exception {
		Crawler.instance.sendSearch(search.getText());
		buttonInfo.setVisible(true);
		buttonReview.setVisible(true);
	}
	
	@FXML
	private void crawlInfo() {
		Crawler.instance.crawlInfo();
	}
	
	@FXML
	private void crawlReview() {
		Crawler.instance.crawlReview();
	}
	
}
