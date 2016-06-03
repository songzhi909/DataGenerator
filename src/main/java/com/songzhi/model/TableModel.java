package com.songzhi.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * 数据表数据对象
 */
public class TableModel {
	/** 表名 */
	private String  tableName;
	/** 表描述 */
	private String  tableDesc;
	/** 列数据 按模型顺序存储*/
	private List<String> columnNames = new ArrayList<String>();
	/** 列模型数据 集合 key:列名 */
	private Map<String, ColumnModel> columnModels = new LinkedHashMap<>();
	
	public ObservableList<ColumnModel> getColumnData() {
		ObservableList<ColumnModel> colData = FXCollections.observableArrayList();
		for(String columnName : columnNames) {
			colData.add(columnModels.get(columnName));
		}
		return colData;
	}
	
	public TableModel() {

	}
	
	public TableModel(String tableName, String tableDesc, List<String> columnNames) {
		super();
		this.tableName = tableName;
		this.tableDesc = tableDesc;
		this.columnNames = columnNames;
	}

	public TableModel(String tableName, String tableDesc, List<String> columnNames,
			Map<String, ColumnModel> columnModels) {
		super();
		this.tableName = tableName;
		this.tableDesc = tableDesc;
		this.columnNames = columnNames;
		this.columnModels = columnModels;
	}

	public String getTableName() {
		return tableName;
	}

	public String getTableDesc() {
		return tableDesc;
	}

	public List<String> getColumnNames() {
		return columnNames;
	}

	public Map<String, ColumnModel> getColumnModels() {
		return columnModels;
	}
	
	public ColumnModel getColumnModel(String columnName) {
		return columnModels.get(columnName);
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setTableDesc(String tableDesc) {
		this.tableDesc = tableDesc;
	}

	public void setColumnNames(List<String> columnNames) {
		this.columnNames = columnNames;
	}

	public void setColumnModels(Map<String, ColumnModel> columnModels) {
		this.columnModels = columnModels;
	}
	
}
