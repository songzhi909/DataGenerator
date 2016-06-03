package com.songzhi.view.diag;

import org.apache.log4j.Logger;

import com.songzhi.generate.Container;
import com.songzhi.lanch.MainApp;
import com.songzhi.utils.ThreadLocalHolder;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

public class ProgressDialogController {
	private static final Logger log = Logger.getLogger(ProgressDialogController.class);

	@FXML
	private ProgressBar progressBar;
	
	@FXML
	private ProgressIndicator progressIndicator;
	
	private MainApp mainApp;
	private Container container;

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		container = mainApp.getContainer();
				
		container.progressValue.addListener(
         (ObservableValue<? extends Number> ov, Number old_val,  Number new_val) -> {
        	 progressBar.setProgress(new_val.doubleValue());
        	 progressIndicator.setProgress(new_val.doubleValue());
     });
	} 
	
	public ProgressBar getProgressBar() {
		return progressBar;
	}
	
	public ProgressIndicator getProgressIndicator() {
		return progressIndicator;
	}
	
	@FXML
	private void initialize() {}

	
}
