package com.crawler.project.view;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;

public class Controller {

	@FXML
	private RadioButton hotels; 
	
	@FXML
	private void start() {
		if(hotels.isSelected()) {
			System.out.println("hotelstart");
		} else {
			System.out.println("start");
		}
		
	}
}
