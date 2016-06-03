package com.songzhi.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Helper class to wrap a list of databases. This is used for saving the
 * list of databases to XML.
 * 
 * @author Marco Jakob
 */
@XmlRootElement(name="databases")
public class DatabaseListWrapper {
	
	private List<DatabaseModel> databases;

	@XmlElement(name = "database")
	public List<DatabaseModel> getDatabasess() {
		if(this.databases == null) {
			this.databases = new ArrayList<DatabaseModel>();
		}
		return databases;
	}

	public void setDatabases(List<DatabaseModel> databases) {
		this.databases = databases;
	}
	
}
