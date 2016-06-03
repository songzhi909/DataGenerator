package com.songzhi.lanch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.songzhi.factory.DataModelGenerator;
import com.songzhi.generate.Container;
import com.songzhi.generate.DataGenerator;
import com.songzhi.model.ColumnModel;
import com.songzhi.model.TableModel;
import com.songzhi.model.TableRuleModel;
import com.songzhi.utils.ThreadLocalHolder;
import com.songzhi.utils.db.DBHelper;
import com.songzhi.view.diag.ProgressDialogController;

public class GenApp implements Launcher {
	private static Logger log = Logger.getLogger(GenApp.class);
	
	private static File[] files;	//存放数据模型文件路径

//	/** 模型数据文件 */
//	private Queue<String>	filenames;
	
	private  Container container; //容器
	
	public static void main(String[] args) {
		try {
//			DataModelGenerator.main(args);
			new GenApp().run();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
	}

	public  Container getContainer() {
		return container;
	}
	
	@Override
	public void run() throws Exception {
		//1.初始化 载入配置与模型文件 
		log.info("初始化...");
		initialize();
		
		//2.将模型文件解析成数据表实体 
		//TODO:考虑开启线程，解析完一个实体，放入队列中
		
//		loadTableRuleModels();

		//3.遍历数据表实体,根据列数据生成策略生成列数据，将生成的业务表数据放入容器中 
		
//		generateData();
			
		//4.数据生成完成后，启动UI界面，展示数据 
//			genDataLatch.await();
		log.info("启动UI...");
		
		UIDisplay();
		
		//5.1组装sql脚本
//		buildScript();
		
		//5.调整业务数据，完成后，确认插入数据库 
		log.info("插入数据...");
//		write2DB();
		
	}

	/**
	 * 载入模型文件解析成数据表实体  
	 */
	public void loadTableRuleModels() throws InterruptedException {
		log.info("读取数据模型文件...");
		final CountDownLatch latch = new CountDownLatch(files.length);	//多线程的协同
		
		for(final File file : files) { 
			
			new Thread(new Runnable(){
				@Override
				public void run() {
					try {
						FileInputStream input = new FileInputStream(file);
						Document doc = new SAXReader().read(input);
						TableRuleModel ruleModel = parseDoc2RuleModel(doc);
						container.tableRuleModels.put(ruleModel.getTableName(), ruleModel);
					} catch (Exception e) {
						log.error(e.getMessage());
					}finally {
						latch.countDown(); //线程数减1
					}
				}
			}).start();
			
		}
		
		latch.await();//等待所有线程完成后执行
	}
	
	/** 清空原始数据*/
	public void clearData() {
		container.tableModels.clear(); // 清空原有数据
	}

	/**
	 * 3.遍历数据表实体,根据列数据生成策略生成列数据，将生成的业务表数据放入容器中 
	 * 	 清空原有数据，然后再生成数据
	 */
	public void generateData() {
		log.debug("生成数据...");
		// final CountDownLatch genDataLatch = new  CountDownLatch(container.tableModels.size()); //多线程的协同
		List<String> tableNames = container.tableNames;
		
		for (String tableName : tableNames) {
			log.debug("---------------------------------------------------------------");
			log.debug("开始生成业务表【" + tableName + "】的数据...");
			TableRuleModel tableRuleModel = container.tableRuleModels.get(tableName);

			List<TableModel> tableModels = generateData(tableRuleModel);

			container.tableModels.put(tableName, tableModels);
		}
	}

	@Override
	public void initialize() {
		//定义应用容器
		container = new Container();
		
		//加载数据模型
		File file = new File("tempDir/xml");
		files = file.listFiles(new FilenameFilter(){
			@Override
			public boolean accept(File dir, String name) {
				boolean flag = name.endsWith(".xml");
				if(flag) {	//将表名放入数组中
					String tableName = name.substring(0, name.length()-4).replaceAll("[\\d|.]", "");
					container.tableNames.add(tableName);
					container.tableDescs.add(DBHelper.getTableDesc(tableName));
				}
				return flag;
			}
		});
		
	}
	
	@Override
	public TableRuleModel parseDoc2RuleModel(Document doc)  throws Exception {
		TableRuleModel tableRuleModel = new TableRuleModel();
			
		Element root = doc.getRootElement();
		Element tableEle = root.element("table");
		
		String tableDesc = root.elementText("name");
		String tableName = tableEle.attributeValue("name");	//表名
		String rows = tableEle.attributeValue("rows");	//数据最大条数
		
		tableRuleModel.setTableName(tableName);
		tableRuleModel.setTableDesc(tableDesc);
		tableRuleModel.setRows(StringUtils.isNotBlank(rows) ? Integer.parseInt(rows) : 1);	//默认1条
		
		// TODO：读取每个列名的注解, 可考虑其他方式
		Iterator<?> nodeIt =  tableEle.nodeIterator();
		while(nodeIt.hasNext()) {
			Node node = (Node) nodeIt.next();
			if(node.getNodeType() == Node.COMMENT_NODE) {
				tableRuleModel.getColumnDescs().add(node.getText());
			}
		}
		
		List<?> columnElements = tableEle.elements("column");
		for(int j=0;j<columnElements.size(); j++) {
			Element colEle = (Element) columnElements.get(j);
			
			//读取列名
			String columnName = colEle.attributeValue("name");
			tableRuleModel.getColumnNames().add(columnName);

			log.debug("处理【" + tableName + "】表的【" + columnName +"】...");
			
			//读取列生成策略
			String genClassName = colEle.selectSingleNode("generator/@type").getStringValue();//生成类
			Class<?> clazz = Class.forName(genClassName); 
			DataGenerator<?> generator = (DataGenerator<?>) clazz.newInstance();
			generator.initialize(container);	//初始化参数
			
			//读取策略的一些设置属性
			List<?> properties = colEle.element("generator").elements("property");
			for(int i=0;i<properties.size();i++) {
				Element propertyEle = (Element) properties.get(i);
				Attribute attr = propertyEle.attribute(0);
				String proName = attr.getName();
				String proValue = attr.getText();
				
				BeanUtils.setProperty(generator, proName, proValue);
			}
			
			tableRuleModel.getGenerators().put(columnName, generator);
			
		}
		
		return tableRuleModel;
	}
	
	@Override
	public List<TableModel> generateData(TableRuleModel tableRuleModel) {
		List<TableModel> tableModels = new ArrayList<>();
		
		//业务数据生成条数
		int rowNum = tableRuleModel.getRows();
		int num = rowNum; //new Random().nextInt(rowNum) + 1;
		
		String tableName = tableRuleModel.getTableName();
		String tableDesc = tableRuleModel.getTableDesc();
		List<String> columnNames = tableRuleModel.getColumnNames();
		List<String> columnDescs = tableRuleModel.getColumnDescs();
		Map<String, DataGenerator<?>> generators = tableRuleModel.getGenerators();
		
		for(int i=0; i<num; i++) {
			Map<String, ColumnModel> columnModels = new LinkedHashMap<>();
			
			TableModel tableModel = new TableModel(tableName, tableDesc, columnNames, columnModels);
			
			int columnIndex = 0;	//列索引
			for(Iterator<?> it = generators.entrySet().iterator(); it.hasNext(); columnIndex++) {
				@SuppressWarnings("unchecked")
				Entry<String, DataGenerator<?>> entry = (Entry<String, DataGenerator<?>>) it.next();
				DataGenerator<?> gen = entry.getValue();	//生成策略
				
				String columnName = entry.getKey();	//列名
				Object columnValue = gen.generator(tableModel);	//生成列数据

				String columnDesc = columnDescs.get(columnIndex);
				
				ColumnModel columnModel = new ColumnModel();
				columnModel.setColumnName(columnName);
				columnModel.setColumnDesc(columnDesc);
				columnModel.setColumnValue(columnValue);	
				columnModel.setColumnSeq(String.valueOf(columnIndex + 1));
				
				columnModels.put(columnName, columnModel);
				
				log.debug("生成字段【" + columnName +"】数据：" + String.valueOf(columnValue));
			}
			
			tableModels.add(tableModel);
		}
		
		return tableModels;
	}
	
	@Override
	public void UIDisplay() {
		MainApp.launch(null, this);
	}
	
	@Override
	public boolean buildScript() {
		boolean result = true;
		try {
			List<String> tableNames = container.tableNames;
			for(String tableName : tableNames) {
				List<String> sqls = new ArrayList<String>();

				for(TableModel tableModel : container.tableModels.get(tableName)) {
					
					Map<String, ColumnModel> columnModels = tableModel.getColumnModels();
					
					StringBuffer sb = new StringBuffer(" insert into interface.").append(tableModel.getTableName()).append("(");
					
					boolean flag = true;	//判断是否第一个
					for(Iterator<Entry<String, ColumnModel>> it = columnModels.entrySet().iterator();it.hasNext();) {
						Entry<String, ColumnModel> entry = it.next();

						if(!flag) sb.append(",");
						if(flag) flag = false;

						sb.append(entry.getKey());  
					}
					sb.append(") values (");
					
					flag = true;
					for(Iterator<Entry<String, ColumnModel>> it = columnModels.entrySet().iterator();it.hasNext();) {
						Entry<String, ColumnModel> entry = it.next();
						ColumnModel columnModel = entry.getValue();
						
						if(!flag) sb.append(",");
						if(flag) flag = false;
						
						Object value = columnModel.getColumnValue();
						if(value == null) value = "";
						
						if(value instanceof String) {
							sb.append("'" + String.valueOf(value) + "'");
						}else if(value instanceof Date){
							Date date = (Date) value;
							String dateStr = DateFormatUtils.format(date, "yyyy-MM-dd");
							sb.append("to_date('" + dateStr+ "', 'yyyy-MM-dd')");
						} else {
							sb.append(String.valueOf(value));
						}
					}
					sb.append(")");
					
					//将sql脚本放入容器中
					sqls.add(sb.toString());
					
				}
				
				container.tableSqls.put(tableName, sqls);
				
			}
		} catch (RuntimeException e) {
			log.error(e.getMessage());
			result = false;
		}
		return result;
	}
	
	private List<String> getSqlArray() {
		List<String> batchSql = new ArrayList<>();
		Map<String, List<String>> tableSqls = container.tableSqls;
				
		for(Iterator<Entry<String, List<String>>> it=tableSqls.entrySet().iterator();it.hasNext();) {
			Entry<String, List<String>> entry = it.next();
			List<String> sqls = entry.getValue();
			batchSql.addAll(sqls);
		}
		return batchSql;
	}
	
	@Override
	public boolean write2DB() {
		log.info("批量插入数据到数据库...");
		boolean flag = true;
		try {
			List<String> batchSql = getSqlArray();
			backupSQL2File(batchSql);
			batchExecuteSql(batchSql);
			//DBHelper.batch(batchSql.toArray());	//批量执行
		} catch (Exception e) {
			log.error(e.getMessage());
			flag = false;
		}
		return flag;
	}
	
	@Override
	public boolean batch2DB() {
		log.info("批量插入数据到数据库...");
		boolean flag = true;
		try {
			List<String> batchSql = new ArrayList<>();
			for(int i=0;i<100;i++) {
				clearData(); 
				generateData();
				buildScript();
				batchSql.addAll(getSqlArray());
			}
			batchExecuteSql(batchSql);
			//DBHelper.batch(batchSql.toArray());	//批量执行
		} catch (Exception e) {
			log.error(e.getMessage());
			flag = false;
		}
		
		return flag;
	}

	/** 备份SQL到文件 */
	private void backupSQL2File(List<String> batchSql) {
		//备份到文档中
		new Runnable() {
			public void run() {
				try {
					File file = new File("script.sql"); 
					StringBuffer sb = new StringBuffer();
					for(String sql : batchSql) {
						sb.append(sql).append(";").append("\n");
					}
					FileUtils.writeStringToFile(file, sb.toString(), "utf-8");
				} catch (IOException e) {
					e.printStackTrace();
				}
			} }.run();;
	}
	
	//批量执行SQL
	private void batchExecuteSql(List<String> batchSql) {
		int size = batchSql.size();
		container.progressValue.set(-1);
		for(int i=0;i<batchSql.size();i++) {
			DBHelper.executeSql(batchSql.get(i));
			container.progressValue.set( (i + 1d) / size);			
			log.debug("插入" + i + " 条数据");
		}
	}

	
}
