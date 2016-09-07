package com.songzhi.view;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import com.songzhi.generate.Container;
import com.songzhi.lanch.MainApp;
import com.songzhi.model.TableModel;
import com.songzhi.model.TableNameAndDesc;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class MainViewController {
	private static Logger log = Logger.getLogger(MainViewController.class);

	@FXML
	private TableView<TableNameAndDesc> tableData;
	@FXML
	private TableColumn<TableNameAndDesc, String> tableNameColumn;	//表明
	@FXML
	private TableColumn<TableNameAndDesc, String> tableDescColumn;	//表描述
	
	@FXML
	private Pagination pagination;
	
	private MainApp mainApp;
	private Container container;	//关联容器
	private TableNameAndDesc tableNameAndDesc;	//当前选中业务
	
	public void setContainer(MainApp mainApp) {
		
		this.mainApp = mainApp;
		
		this.container = mainApp.getContainer();
		
		tableData.setItems(this.container.getTableNameAndDescs());
		
		selectFirstRecord();
	}
		
	/** 当MainView.fxml被载入时，自动调用*/
	@FXML
	private void initialize() {
		//动态绑定表信息
		tableNameColumn.setCellValueFactory(cellData -> cellData.getValue().tableNameProperty());
		tableDescColumn.setCellValueFactory(cellData -> cellData.getValue().tableDescProperty());

		//监听表选中事件
		tableData.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> selectTable(newValue));
		
    pagination.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
	}
	
	/** 选中某张表，显示该表生成的数据*/
	private void selectTable(TableNameAndDesc tableNameAndDesc) {
		if( tableNameAndDesc != null) {
			this.tableNameAndDesc = tableNameAndDesc;
			
			List<TableModel> tableModels = container.tableModels.get(tableNameAndDesc.getTableName());
			
			if(tableModels == null || tableModels.size() == 0)  return;
			
			int pageCount = tableModels.size();
			pagination.setPageCount(pageCount);
			pagination.setPageFactory(num -> createPage(num));
			
		}else {
//			columnData.setItems(null);
		}

	}

	/** 显示某条表格详细 */
	private AnchorPane createPage(Integer num) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource("com/songzhi/view/TableModelView.fxml"));
			AnchorPane anchorPane = loader.load();

			TableModel tableModel = container.tableModels.get(tableNameAndDesc.getTableName()).get(num);
			
			TableModelViewController controller = loader.getController();
			controller.setContainer(container, tableModel);

			return anchorPane;
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return null;
	}
	
	/**  生成数据 */
	@FXML
	private void genData() {
		mainApp.getGenApp().clearData();
		
		selectFirstRecord();
		
		//TODO 提示正在重新生成数据
		mainApp.getGenApp().generateData();
		
		selectFirstRecord();
	}
	
	/** 将生成的表数据插入数据库中*/
	@FXML
	private void batchDB() {

		mainApp.getGenApp().buildScript();
		 
		mainApp.showProgressDialog();			//显示插入进度条
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				mainApp.getGenApp().write2DB();
				
			}
		}).start();;
	}
	
	/**
	 * 批量生成数据与写入
	 */
	@FXML
	private void batchGenAndInsert() {

//		selectFirstRecord();
		mainApp.showProgressDialog();			//显示插入进度条
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				mainApp.getGenApp().batch2DB();
			}
		}).start();;
		
		
	}

	private void selectFirstRecord() {
		TableNameAndDesc tableNameAndDesc = new TableNameAndDesc();
		tableNameAndDesc.setTableName(container.tableNames.get(0));//默认显示第一条数据
		selectTable(tableNameAndDesc); 
	}
}
