package com.songzhi.view.db;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.controlsfx.dialog.CommandLinksDialog;
import org.controlsfx.dialog.CommandLinksDialog.CommandLinksButtonType;

import com.songzhi.lanch.MainApp;
import com.songzhi.model.DatabaseModel;

import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * 数据库连接信息控制器
 * @author songzhi
 *
 */
public class DatabaseController {
	private static final Logger log = Logger.getLogger(DatabaseController.class);
	
	@FXML
	private TableView<DatabaseModel> databaseData;
	
	@FXML
	private TableColumn<DatabaseModel, String> dbNameColumn;
	@FXML
	private TableColumn<DatabaseModel, String> dbUrlColumn;
	@FXML
	private TableColumn<DatabaseModel, String> avaiableFlagColumn;
	@FXML
	private TableColumn<DatabaseModel, String> defaultFlagColumn;
	
	@FXML
	private void initialize() {
		dbNameColumn.setCellValueFactory(cellData -> cellData.getValue().dbNameProperty());
		dbUrlColumn.setCellValueFactory(cellData -> cellData.getValue().dbUrlProperty());
		avaiableFlagColumn.setCellValueFactory(cellData -> cellData.getValue().avaiableFlagProperty());
		defaultFlagColumn.setCellValueFactory(cellData -> cellData.getValue().defaultFlagProperty());
	}

	private MainApp mainApp;
	private Stage dialogStage;
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		
		
		databaseData.setItems(mainApp.getDatabaseData());
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	@FXML
	public void addHandler() {
		DatabaseModel tempModel = new DatabaseModel();
		boolean okClicked = showEditDialog(tempModel);
		if(okClicked) {
			mainApp.getDatabaseData().add(tempModel);
		}
	}
	
	public boolean showEditDialog(DatabaseModel databaseModel) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getClassLoader().getResource("com/songzhi/view/db/DBEditView.fxml"));
			AnchorPane page = loader.load(); 
			
			// create the dialog stage
			Stage dialogStage = new Stage();
			dialogStage.setTitle("添加数据库信息");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(mainApp.getPrimaryStage());
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			
			DBEditController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setDataBaseModel(databaseModel);
			
			//show the dialog and wait until the user closes it
			dialogStage.showAndWait();
			
			
			return controller.isOkclicked();
			
		} catch (IOException e) {
			log.error(e.getMessage());
			return false;
		}
	}
	
	@FXML 
	void editHandler() {
		DatabaseModel selectedModel = databaseData.getSelectionModel().getSelectedItem();
		if(selectedModel != null) {
			 showEditDialog(selectedModel);
		}
	}

	@FXML 
	public void deleteHandler() {
		int selectedIndex = databaseData.getSelectionModel().getSelectedIndex();
		if(selectedIndex >= 0) {
			databaseData.getItems().remove(selectedIndex);
		}else {
			CommandLinksButtonType links = new CommandLinksButtonType("提示", "longtext", true);
			CommandLinksDialog dialog = new CommandLinksDialog(links);
			dialog.setTitle("提示");
			dialog.show();
		}
	}
}
