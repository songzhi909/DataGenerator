package com.songzhi.view.tablerule;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.songzhi.factory.DataModelGenerator;
import com.songzhi.generate.Container;
import com.songzhi.lanch.MainApp;
import com.songzhi.model.TableNameAndDesc;
import com.songzhi.utils.FileUtils;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

public class TableRuleController {
	private static final Logger log = Logger.getLogger(TableRuleController.class);

	@FXML
	private TableView<TableNameAndDesc> tableData;
	@FXML
	private TableColumn<TableNameAndDesc, String> tableNameColumn;	//表明
	@FXML
	private TableColumn<TableNameAndDesc, String> tableDescColumn;	//表描述

	private String  tableName;	//当前选中业务
	
	@FXML
	private TextArea textArea;
	
	private MainApp mainApp;
	
	public void setContainer(MainApp mainApp) {
		this.mainApp = mainApp;

		showRule();
	}

	/**显示规格 */
	private void showRule() {
		log.debug("显示规格模型界面");
		
		Container container = mainApp.getContainer();
		
		if(tableData.getItems() != null) tableData.getItems().clear();
		
		if(container.tableNames != null && container.tableNames.size()>0) {
		
			tableData.setItems(container.getTableNameAndDescs());
		
			selectTableRuleModel(container.tableNames.get(0)); //默认显示第一条数据
		}
	}
	
	/** 当MainView.fxml被载入时，自动调用*/
	@FXML
	private void initialize() {
		try {
			//动态绑定表信息
			tableNameColumn.setCellValueFactory(cellData -> cellData.getValue().tableNameProperty());
			tableDescColumn.setCellValueFactory(cellData -> cellData.getValue().tableDescProperty());
			
			//监听表选中事件
			tableData.getSelectionModel().selectedItemProperty().addListener(
					(observable, oldValue, newValue) -> selectTableRuleModel(newValue.getTableName()));
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
	}
	
	/** 生成规则对象 */
	@FXML
	private void genRules() {
		try {
			log.debug("准备生成模型规则...");
			//生成规则对象
			DataModelGenerator generator =  new DataModelGenerator();
			generator.generateTableRuleModels();

			log.debug("模型规则生成结束");
			//加载规格对象
			mainApp.getContainer().loadTableRules();

			showRule();
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	/** 保存修改*/
	@FXML
	private void saveOrUpdate() {
		if(textArea != null) {
			try {
				String data = textArea.getText();

				File file = FileUtils.searchXml(tableName);
				
				org.apache.commons.io.FileUtils.writeStringToFile(file, data, "utf-8");
			} catch (IOException e) { 
				log.error(e.getMessage());
			}
		}
	}
	
	/** 显示业务表规则模型*/
	private void selectTableRuleModel(String tableName) {
		log.debug("select table: " + tableName);
		if( tableName != null) {
			try {
				this.tableName = tableName;
				
				File file = FileUtils.searchXml(tableName);
				String content = org.apache.commons.io.FileUtils.readFileToString(file, "utf-8");
				textArea.setText(content);
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		} 
	
	}

}
