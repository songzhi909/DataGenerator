package com.songzhi.generate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.songzhi.model.TableModel;
import com.songzhi.model.TableNameAndDesc;
import com.songzhi.model.TableRuleModel;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** 容器 存储生成的数据 */
public class Container {

	/** 存放业务表名列表 */
	public List<String> tableNames = new ArrayList<String>();
	/** 存放业务表描述列表 */
	public List<String> tableDescs = new ArrayList<String>();
	
	/** 业务表 生成策略 集合 key:表名*/
	public Map<String, TableRuleModel> tableRuleModels = new ConcurrentHashMap<>();
	
	/**业务表 数据 集合  key:表名*/ 
	public Map<String, List<TableModel>> tableModels = new ConcurrentHashMap<>();

	/** 进度条数值 */
	public DoubleProperty progressValue = new SimpleDoubleProperty(-1d);
	
	/** 存放业务执行sql  key:表名*/
	public Map<String, List<String>> tableSqls = new LinkedHashMap<String, List<String>>();
	
	/** 获取业务生成规则集合 */
	public ObservableList<TableRuleModel> getTableData() {
		ObservableList<TableRuleModel> tableNameAndDescs = FXCollections.observableArrayList();
		for(String tableName : tableNames) {		 
			tableNameAndDescs.add(this.tableRuleModels.get(tableName));
		}
		return tableNameAndDescs;
	}
	
	/** 获取业务生成规则集合 */
	public ObservableList<TableNameAndDesc> getTableNameAndDescs() {
		ObservableList<TableNameAndDesc> tableNameAndDescs = FXCollections.observableArrayList();
		for(int i=0; i< tableNames.size(); i++) { 
		 
		 TableNameAndDesc bean = new TableNameAndDesc();
		 bean.setTableName(tableNames.get(i));
		 bean.setTableDesc(tableDescs.get(i));
		 
		 tableNameAndDescs.add(bean);
		}
		return tableNameAndDescs;
	}
	
}
