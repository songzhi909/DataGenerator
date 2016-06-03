package com.songzhi.model;

import org.apache.commons.lang.StringUtils;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 列信息描述
 */
public class ColumnModel {

	/** 列名 */
	private final  StringProperty columnName = new SimpleStringProperty();
	/** 列描述 */
	private final  StringProperty columnDesc = new SimpleStringProperty();
	/** 列顺序号 */
	private final  StringProperty columnSeq = new SimpleStringProperty();
	/** 列的值 */
	private  Object  columnValue;
	/** 列的显示值 */
	private final  StringProperty columnDisplayValue = new SimpleStringProperty();

	/** 数据类型 */
	private String dataType;
	private Integer dataLength;
	private String nullable;
	private Integer dataPrecision;
	private Integer dataScale;

	public String getDataType() {
		String type = "";
		if("CHAR".equals(dataType) || "VARCHAR2".equals(dataType) || "CLOB".equals(dataType) || "RAW".equals(dataType) || "NVARCHAR2".equals(dataType)) {
			type = "string";
		} else if("LONG".equals(dataType)) {
			type = "long";
		} else if("NUMBER".equals(dataType) && dataScale == null) {
			type = "long";
		}else if("NUMBER".equals(dataType) && dataScale != 0 ) {
			type = "double";
		}else if("NUMBER".equals(dataType) && dataScale == 0) {
			type = "integer";
		}else if("DATE".equals(dataType)) {
			type = "date";
		}else if("TIMESTAMP".equals(dataType)) {
			type = "datetime";
		}
		
		if(StringUtils.isEmpty(type)) {
			throw new RuntimeException("类型:" + type + ", 没有可匹配类型");
		}
		return type;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public int getDataLength() {
		return dataLength;
	}

	public void setDataLength(int dataLength) {
		this.dataLength = dataLength;
	}

	public String getNullable() {
		return nullable;
	}

	public void setNullable(String nullable) {
		this.nullable = nullable;
	}

	public int getDataPrecision() {
		return dataPrecision;
	}

	public void setDataPrecision(int dataPrecision) {
		this.dataPrecision = dataPrecision;
	}

	public int getDataScale() {
		return dataScale;
	}

	public void setDataScale(int dataScale) {
		this.dataScale = dataScale;
	}
	public void setDataLength(Integer dataLength) {
		this.dataLength = dataLength;
	}

	public void setDataPrecision(Integer dataPrecision) {
		this.dataPrecision = dataPrecision;
	}

	public void setDataScale(Integer dataScale) {
		this.dataScale = dataScale;
	}

	
	public StringProperty columnNameProperty() {
		return columnName;
	}
	
	public String getColumnName() {
		return columnName.get();
	}

	public void setColumnName(String columnName) {
		this.columnName.set(columnName);
	}
	
	public StringProperty columnDescProperty() {
		return columnDesc;
	}
	
	public String getColumnDesc() {
		return columnDesc.get();
	}

	public void setColumnDesc(String columnDesc) {
		this.columnDesc.set(columnDesc);
	}
	
	public StringProperty columnDisplayValueProperty() {
		return columnDisplayValue;
	}
	
	public String getColumnDisplayValue() {
		return columnDisplayValue.get();
	}
	
	public StringProperty columnSeqProperty() {
		return columnSeq;
	}
	
	public String getColumnSeq() {
		return columnSeq.get();
	}
	
	public void setColumnSeq(String columnSeq) {
		this.columnSeq.set(columnSeq);
	}

	public Object getColumnValue() {
		return columnValue;
	}

	public void setColumnValue(Object columnValue) {
		this.columnValue = columnValue;
		this.columnDisplayValue.set(StringUtils.defaultIfEmpty(String.valueOf(columnValue), ""));
	}

}
