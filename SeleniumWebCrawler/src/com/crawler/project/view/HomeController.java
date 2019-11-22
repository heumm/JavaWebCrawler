package com.crawler.project.view;

import com.crawler.project.Main;
import com.crawler.project.application.Crawler;
import com.crawler.project.application.HotelsCrawler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;

public class HomeController {

	@FXML
	private RadioButton hotels; 
	
	@FXML	
	private void start() throws Exception{
		if(hotels.isSelected()) {
			Crawler.instance = new HotelsCrawler();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/HotelsView.fxml"));
			AnchorPane hotelsView = (AnchorPane)loader.load();
			
			Scene scene = new Scene(hotelsView);
			Main.getPrimaryStage().setScene(scene);
		} 
	}

}
