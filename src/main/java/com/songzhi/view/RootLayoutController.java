package com.songzhi.view;

import com.songzhi.lanch.MainApp;

import javafx.fxml.FXML;

public class RootLayoutController {

	private MainApp mainApp;
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	/**显示数据库连接*/
	@FXML
	public void  handlerShowDatabase() {
		mainApp.loadDatabaseFromFile();
		mainApp.showDatabase();
		mainApp.saveDatabaseToFile();
	}
	
	@FXML
	public void showDict() {
		mainApp.loadDicts();
	}
	
	@FXML
	public void showTables() {
		
	}
	
	@FXML
	public void showTableRule() {
		mainApp.showTableRule();
	}
	
	@FXML
	private void handlerDataGenerator() {
		//TODO:生成数据
		try {
			mainApp.getGenApp().loadTableRuleModels();
		} catch (InterruptedException e) { 
			e.printStackTrace();
		}
		//显示生成的数据
		mainApp.showMainView();
	}
	
}
