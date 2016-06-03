package com.songzhi.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.songzhi.generate.DataGenerator;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 业务表生成策略
 * @author songzhi
 *
 */
public class TableRuleModel {

	/** 表名 */
	private final StringProperty  tableName = new SimpleStringProperty();
	/** 表描述 */
	private final  StringProperty  tableDesc = new SimpleStringProperty();
	/** 业务表的列字段集合	 按顺序排序*/
	private List<String> columnNames = new ArrayList<String>();
	/** 业务表的列描述集合 	 按顺序排序*/
	private List<String> columnDescs = new ArrayList<String>();
	/** 列生成策略 按模型顺序存储  key:列名*/
	private Map<String, DataGenerator<?>> generators = new LinkedHashMap<String, DataGenerator<?>>();
	/** 业务数据最大生成条数 */
	private int rows;

	public StringProperty tableNameProperty() {
		return tableName;
	}

	public String getTableName() {
		return tableName.get();
	}
	
	public void setTableName(String tableName) {
		this.tableName .set(tableName);
	}
	
	public StringProperty tableDescProperty() {
		return tableDesc;
	}

	public String getTableDesc() {
		return tableDesc.get();
	}

	public void setTableDesc(String tableDesc) {
		this.tableDesc.set(tableDesc);
	}

	public Map<String, DataGenerator<?>> getGenerators() {
		return generators;
	}

	public void setGenerators(Map<String, DataGenerator<?>> generators) {
		this.generators = generators;
	}

	public List<String> getColumnNames() {
		return columnNames;
	}

	public List<String> getColumnDescs() {
		return columnDescs;
	}

	public void setColumnNames(List<String> columnNames) {
		this.columnNames = columnNames;
	}

	public void setColumnDescs(List<String> columnDescs) {
		this.columnDescs = columnDescs;
	}
	
	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}
}
