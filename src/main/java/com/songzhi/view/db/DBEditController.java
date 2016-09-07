package com.songzhi.view.db;

import com.songzhi.model.DatabaseModel;

import javafx.fxml.FXML;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DBEditController {
	
	@FXML
	private TextField dbNameField;
	@FXML
	private TextField dbUrlField;
	@FXML
	private TextField dbUserNameField;
	@FXML
	private TextField dbPasswordField;
	@FXML
	private TextField defaultFlagField;
//	@FXML
//	private TextField avaiableFlagField;

	private DatabaseModel databaseModel;
	private Stage dialogStage;
	private boolean okclicked = false;

	/**
   * Initializes the controller class. This method is automatically called
   * after the fxml file has been loaded.
   */
  @FXML
  private void initialize() {
  }
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setDataBaseModel(DatabaseModel databaseModel) {
		this.databaseModel = databaseModel;
		
		dbNameField.setText(databaseModel.getDbName());
		dbUrlField.setText(databaseModel.getDbUrl());
		dbUserNameField.setText(databaseModel.getDbUserName());
		dbPasswordField.setText(databaseModel.getDbPassword());
		defaultFlagField.setText(databaseModel.getDefaultFlag());
	}

	public boolean isOkclicked() {
		return okclicked;
	}

	@FXML
  private void handleOk() {
  	if(isInputValid()) {
  		databaseModel.setDbName(dbNameField.getText()); 
  		databaseModel.setDbUrl(dbUrlField.getText()); 
  		databaseModel.setDbUserName(dbUserNameField.getText()); 
  		databaseModel.setDbPassword(dbPasswordField.getText()); 
  		databaseModel.setDefaultFlag(defaultFlagField.getText());
  		
  		okclicked = true;
  		dialogStage.close();
  	}
  }
  
  private boolean isInputValid() {
  	 String errorMessage = "";

     if (dbNameField.getText() == null || dbNameField.getText().length() == 0) {
         errorMessage += "No valid database name!\n"; 
     }
     if (dbUrlField.getText() == null || dbUrlField.getText().length() == 0) {
         errorMessage += "No valid database url!\n"; 
     }
     if (dbUserNameField.getText() == null || dbUserNameField.getText().length() == 0) {
         errorMessage += "No valid username!\n"; 
     }

     if (dbPasswordField.getText() == null || dbPasswordField.getText().length() == 0) {
         errorMessage += "No valid password!\n"; 
     } 

     if (errorMessage.length() == 0) {
         return true;
     } else {
     	ButtonType loginButtonType = new ButtonType("Login", ButtonData.OK_DONE);
     	 Dialog<String> dialog = new Dialog<>();
     	 dialog.getDialogPane().getButtonTypes().add(loginButtonType);
     	 boolean disabled = true; // computed based on content of text fields, for example
     	 dialog.getDialogPane().lookupButton(loginButtonType).setDisable(disabled);
         return false;
     }
	}

	@FXML
  private void handleCancel() {
  	dialogStage.close();
  }
	
}
