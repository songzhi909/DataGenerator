package com.songzhi.generate;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.songzhi.model.TableModel;
import com.songzhi.model.TableNameAndDesc;
import com.songzhi.model.TableRuleModel;
import com.songzhi.utils.db.DBHelper;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** 容器 存储生成的数据 */
public class Container {
	
	public static final String RESOURCES_FOLDER = "resources/";	//程序路径
//	public static final String RESOURCES_FOLDER = "./";	//exe程序路径

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
	
	public File[] files;	//存放数据模型文件路径
	
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
	
	/** 加载业务模型规则文件  */
	public void loadTableRules() {
		File file = new File(RESOURCES_FOLDER + "xml");
		
		tableNames.clear();
		tableDescs.clear();
		
		files = file.listFiles((dir, name)-> {
				boolean flag = name.endsWith(".xml");
				if(flag) {	//将表名放入数组中
					String tableName = name.substring(0, name.length()-4).replaceAll("(\\d*\\.)", "");
					tableNames.add(tableName);
					tableDescs.add(DBHelper.getTableDesc(tableName));
				}
				return flag;
			}
		);
	}
	
}
