package com.crawler.project;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

	private Stage primaryStage;
	
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Crawler");


		showOverview();
	}
	/**
	 * 상위 레이아웃을 초기화한다.
	 */

	/**
	 * 상위 레이아웃 안에 연락처 요약(person overview)을 보여준다.
	 */
	public void showOverview() {
		try {
			// 연락처 요약을 가져온다.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/Overview.fxml"));
			AnchorPane overview = (AnchorPane) loader.load();

			// 연락처 요약을 상위 레이아웃 가운데로 설정한다.
//			rootLayout.setCenter(personOverview);
			Scene scene = new Scene(overview);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 메인 스테이지를 반환한다.
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
