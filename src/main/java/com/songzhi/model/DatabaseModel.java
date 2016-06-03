package com.songzhi.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 数据库连接
 * @author songzhi
 *
 */
public class DatabaseModel {

	private final StringProperty dbName = new SimpleStringProperty("");		//数据库别名
	private final StringProperty dbUrl= new SimpleStringProperty("");		//数据库连接地址
	private final StringProperty dbUserName = new SimpleStringProperty("");		//用户名
	private final StringProperty dbPassword = new SimpleStringProperty("");		//密码
	private final StringProperty defaultFlag =  new SimpleStringProperty("");		//默认连接
	private final StringProperty avaiableFlag = new SimpleStringProperty("");		//是否可用
	
	public StringProperty dbNameProperty() {
		return dbName;
	}
	
	public String getDbName() {
		return dbName.get();
	}

	public void setDbName(String dbName) {
		this.dbName.set(dbName);
	}

	public StringProperty dbUrlProperty() {
		return dbUrl;
	}
	
	public String getDbUrl() {
		return dbUrl.get();
	}
	
	public void setDbUrl(String dbUrl) {
		this.dbUrl.set(dbUrl);
	}

	public StringProperty dbUserNameProperty() {
		return dbUserName;
	}
	
	public String getDbUserName() {
		return dbUserName.get();
	}
	
	public void setDbUserName(String dbUserName) {
		this.dbUserName.set(dbUserName);
	}

	public StringProperty dbPasswordProperty() {
		return dbPassword;
	}
	
	public String getDbPassword() {
		return dbPassword.get();
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword.set(dbPassword);
	}
	
	public StringProperty defaultFlagProperty() {
		return defaultFlag;
	}
	
	public String getDefaultFlag() {
		return defaultFlag.get();
	}

	public void setDefaultFlag(String defaultFlag) {
		this.defaultFlag.set(defaultFlag);
	}
	
	public StringProperty avaiableFlagProperty() {
		return avaiableFlag;
	}
	
	public String getAvaiableFlag() {
		return avaiableFlag.get();
	}

	public void setAvaiableFlag(String avaiableFlag) {
		this.avaiableFlag.set(avaiableFlag);
	}
}
