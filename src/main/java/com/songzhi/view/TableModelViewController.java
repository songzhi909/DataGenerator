package com.songzhi.view;

import com.songzhi.generate.Container;
import com.songzhi.model.ColumnModel;
import com.songzhi.model.TableModel;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;

public class TableModelViewController {

	@FXML
	private TableView<ColumnModel> columnData;

	@FXML
	private TableColumn<ColumnModel, String> columnSeqNoColumn;	//列名
	@FXML
	private TableColumn<ColumnModel, String> columnValueColumn;	//列描述
	@FXML
	private TableColumn<ColumnModel, String> columnNameColumn;	//列名
	@FXML
	private TableColumn<ColumnModel, String> columnDescColumn; //列值
	
//	private Container container;	//关联容器
//	private TableModel tableModel;
	
	public void setContainer(Container container, TableModel tableModel) {
//		this.container = container;
//		this.tableModel = tableModel;
		
		ObservableList<ColumnModel> data = tableModel.getColumnData();
		
		this.columnData.setItems(data);
	}
	
	@FXML
	private void initialize() {

		//动态绑定列信息
		columnSeqNoColumn.setCellValueFactory(cellData -> cellData.getValue().columnSeqProperty());
		columnDescColumn.setCellValueFactory(cellData -> cellData.getValue().columnDescProperty());
		columnNameColumn.setCellValueFactory(cellData -> cellData.getValue().columnNameProperty());
		columnValueColumn.setCellValueFactory(cellData -> cellData.getValue().columnDisplayValueProperty()); 
		
		columnValueColumn.setCellFactory(TextFieldTableCell.forTableColumn());	//需设置输入域才能编辑
		columnValueColumn.setOnEditCommit(t -> t.getTableView().getItems().get(t.getTablePosition().getRow()).setColumnValue(t.getNewValue()));		//设置新值
	}
	
	
}
