package com.songzhi.lanch;

import java.util.List;

import org.dom4j.Document;

import com.songzhi.model.TableModel;
import com.songzhi.model.TableRuleModel;

/***
 * 数据生成器
 *  <br/>
 *  &nbsp;&nbsp;业务流程：
 * 	<ul>
 * 		<li>1.初始化 载入配置与模型文件</li>
 * 		<li>2.将模型文件解析成数据表实体</li>
 * 		<li>3.遍历数据表实体,根据列数据生成策略生成列数据，将生成的业务表数据放入容器中</li>
 * 		<li>4.数据生成完成后，启动UI界面，展示数据</li>
 * 		<li>5.调整业务数据，完成后，确认插入数据库</li> 
 *  </ul>
 */
public interface Launcher {

	/** 运行 */
	public void run() throws Exception;

	/** 1. 初始化 载入配置与模型对象 */
	public void initialize();
	
	
	/***
	 * 2. 将模型文件解析成数据表生成策略实体， 列生成策略放入容器中
	 */
	public TableRuleModel parseDoc2RuleModel(Document doc) throws Exception;
	
	/**
	 * 3.遍历数据表实体,根据列数据生成策略生成列数据，将生成的业务表数据放入容器中
	 * @param tableModel
	 * @return
	 */
	public List<TableModel> generateData(TableRuleModel tableRuleModel);
	
	/**
	 * 4.数据生成完成后，启动UI界面，展示数据
	 */
	public void UIDisplay();
	
	/**
	 * 5.组装sql脚本
	 */
	public boolean buildScript();
	
	/**
	 * 6.调整业务数据，完成后，确认插入数据库
	 * @return
	 */
	public boolean write2DB();
	
	/** 批量插入数据库 */
	public boolean batch2DB();
}
