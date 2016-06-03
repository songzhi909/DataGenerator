package com.songzhi.generate;

import java.util.List;

import org.apache.log4j.Logger;

import com.songzhi.model.TableModel;

/**
 * 关联表字段生成器
 */
public class RelationGenerator extends DataGeneratorAdapter {
	private static Logger log = Logger.getLogger(RelationGenerator.class);
	
	/** 关联数据 */
	private String tableName;
	/** 关联字段 */
	private String columnName;

	@Override
	public Object generator(TableModel tableModel) {
		log.debug("准备关联【" +tableName + "】的字段【" + columnName + "】...");
		TableModel relationTableModel =  filterTableModel(tableModel);		//关联业务表
		
		if(relationTableModel == null) throw new RuntimeException("查询不到【" + tableName +"】的数据！！！");
		Object value = relationTableModel.getColumnModel(columnName).getColumnValue();
		log.debug("关联【" + relationTableModel.getTableName() + "】的字段【" + columnName + "】:" + String.valueOf(value));
		return value;
	}

	/**
	 * 根据表的主键过滤关联表
	 * @param currentTableData
	 * @return
	 */
	private TableModel filterTableModel(TableModel tableModel) {
		TableModel relationTableModel = null;
		List<TableModel> relationTableModels = container.tableModels.get(tableName);
		relationTableModel = relationTableModels.get(0);	//TODO: 根据主键关联
		return relationTableModel;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
}
