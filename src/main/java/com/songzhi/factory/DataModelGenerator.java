package com.songzhi.factory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.songzhi.model.ColumnModel;
import com.songzhi.utils.OrderedProperties;
import com.songzhi.utils.db.DBHelper;

/**
 * 数据模型文件生成器
 * 
 * 模拟一个人的一次就诊记录(门诊/住院)
 */
public class DataModelGenerator {
	private static Logger log = Logger.getLogger(DataModelGenerator.class);
	
	public static void main(String[] args)  {
//		String sql = "select table_name from dba_all_tables t where t.owner = 'INTERFACE'";
//		List<?> tableNames = DBHelper.query(sql);
		
		try {
			DataModelGenerator generator =  new DataModelGenerator();
			generator.generateTableRuleModels();
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public  void  generateTableRuleModels() throws Exception {
		log.info("开始加载业务字典...");
		InputStream in = DataModelGenerator.class.getClassLoader().getResourceAsStream("gen.properties");
		OrderedProperties props = new OrderedProperties();
		props.load(new InputStreamReader(in, "utf-8"));
		log.info("加载业务字典完成！");
		
		File tempDirFile = new File("tempDir/xml");
		if(!tempDirFile.exists()) tempDirFile.mkdirs();

		log.info("开始生成业务规则文件 ...");
		for(Object key : props.keySet()) {
			String tableName = props.getProperty(key.toString());
			String filename = "tempDir/xml/" + key.toString()+"." + tableName + ".xml"; 
			genernateXml( tableName ,filename, String.valueOf(key.toString().charAt(0)));
		}
		log.info("生成业务规则文件完成！");
	}

	/** 构建xml 文件*/
	private void genernateXml(String tableName, String filename, String type) throws Exception {
		try {

			log.info("开始生成业务规则【" + tableName + "】...");
			
			String tableNameDesc = DBHelper.getTableDesc(tableName);
			
			String sql = "select t.COLUMN_NAME as columnName, t.DATA_TYPE as dataType, t.DATA_LENGTH as dataLength, t.NULLABLE as nullable,T.DATA_PRECISION as dataPrecision, T.DATA_SCALE as dataScale,C.COMMENTS as columnDesc from all_tab_columns t, all_col_comments c where t.OWNER = c.OWNER and t.TABLE_NAME = c.TABLE_NAME and t.COLUMN_NAME = c.COLUMN_NAME and t.table_name='"+tableName+"' order by t.COLUMN_ID";
			
			log.debug(sql); 
			
			List<?> list = DBHelper.query(sql, ColumnModel.class);
			
			Document doc = DocumentHelper.createDocument();

//			doc.addDocType("dbmonster-schema", "-//kernelpanic.pl//DBMonster Database Schema DTD 1.1//EN", "http://dbmonster.kernelpanic.pl/dtd/dbmonster-schema-1.1.dtd");
			
			Element schemaEle = doc.addElement("dbmonster-schema");
			
			
			schemaEle.addElement("name").addText(tableNameDesc);

			Element rootEle = schemaEle.addElement("table");
			
			rootEle.addAttribute("name", tableName);
			rootEle.addAttribute("rows", "1");		//默认只插入一条数据
			
			for(Object obj : list) {
				ColumnModel model = (ColumnModel) obj;
				
				if(StringUtils.isNotBlank(model.getColumnDesc())) rootEle.addComment(model.getColumnDesc());
				else rootEle.addComment(model.getColumnName());
					
				Element colEle = rootEle.addElement("column");
				colEle.addAttribute("name", model.getColumnName());
				colEle.addAttribute("databaseDefault", "false");	//是否用默认值

				Element generatorEle = colEle.addElement("generator");
				
				//一些特殊字段生成相应生成器
				boolean flag = false; 
				
				flag = columnSpecifyGenRule(generatorEle, model, tableName);
				
				if(flag) continue;	//如果已经生成字段生成策略，则跳到下一个字段
				
				flag = columnLikeGenRule(generatorEle, model, tableName);
				
				if(flag) continue;	//如果已经生成字段生成策略，则跳到下一个字段
					
				flag = columnDictGenRule(generatorEle, model);
				
				if(flag) continue;	//如果已经生成字段生成策略，则跳到下一个字段
				
				flag = relationRule(generatorEle, model, type);
				
				if(flag) continue;	//如果已经生成字段生成策略，则跳到下一个字段
				
				//列类型生成策略
				flag = columnTypeGenRule(generatorEle, model);
				
				if(!flag) throw new RuntimeException("【" + model.getColumnName() + "】无法生成策略：" + model.toString());
					
			}
			
			//遍历doc上的字典字段的生产策略,看是否有相同的字段生成策略
			Map<String, String> map = new HashMap<String, String>();	//存放  字典与字段对
			
			List<?> generatorNodes = rootEle.selectNodes("column/generator[contains(@type,'Dictionary')]");
			for(int i=0;i<generatorNodes.size();i++) {
				Node node = (Node) generatorNodes.get(i);
				
				String genType = node.selectSingleNode("@type").getStringValue();
				String key = "";
				if("com.songzhi.generate.DictionaryFromFileGenerator".equals(genType)) {
					key = node.selectSingleNode("property/@dictName").getStringValue();
				}else if("com.songzhi.generate.DictionaryFromDBGenerator".equals(genType)) {
					key = node.selectSingleNode("property/@classCode").getStringValue();
				}else if("com.songzhi.generate.DictionaryFromDataGenerator".equals(genType)) {
					key = node.selectSingleNode("property/@content").getStringValue();
				}
				
				if(!map.keySet().contains(key)) {
					map.put(key, node.getParent().attributeValue("name"));
				}else {
					String targetColumn = map.get(key);
					((Element)node).addAttribute("relationColumn", targetColumn);	//添加关联字段
				}
			}
			
			OutputFormat format = OutputFormat.createPrettyPrint(); //设置XML文档输出格式
			format.setEncoding("UTF-8"); //设置XML文档的编码类型
//			format.setSuppressDeclaration(true);
//			format.setIndent(true); //设置是否缩进
//			format.setIndent(" "); //以空格方式实现缩进
//			format.setNewlines(true); //设置是否换行
			
			File file = new File(filename);
			XMLWriter out = new XMLWriter(new FileOutputStream(file), format);
			out.setEscapeText(false);
//			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
			out.write(doc);
			out.flush();
			out.close();

			log.debug("生成业务规则【" + tableName + "】结束！");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("生成业务规则【" + tableName + "】失败,错误原因：" + e.getMessage());
//			throw e;
		}
		
	}

	/** 指定生成规则    */
	private  boolean columnSpecifyGenRule(Element generatorEle,ColumnModel model, String tableName) throws Exception{

		InputStream in = DataModelGenerator.class.getClassLoader().getResourceAsStream("rule/specify.properties");
		OrderedProperties props = new OrderedProperties();
		props.load(new InputStreamReader(in, "utf-8"));
		
		boolean flag = true;
		String columnName = model.getColumnName();
		if(props.containsKey(tableName + "&" + columnName) || props.containsKey(columnName)) {
			String key = props.containsKey(columnName) ? columnName :  tableName + "&" + columnName;
			String value = props.getProperty(key).replaceAll("%tableName%", tableName);	//DictionaryFromFileGenerator&dictType=1&random=true&dictName=COMM_IHE_AUTHORITY
			String[] arr = value.split("&");
			generatorEle.addAttribute("type", "com.songzhi.generate."+ arr[0]);
			if(arr.length > 1) {
				for(int i=1;i<arr.length;i++) {
					String[] nameAndValue = arr[i].split("=");
					generatorEle.addElement("property").addAttribute(nameAndValue[0],  nameAndValue[1]); 
				}
			}
		}else {
			flag =false;
		}
		
		return flag;
	}
	
	private  boolean columnLikeGenRule(Element generatorEle,ColumnModel model, String tableName) throws Exception{
		InputStream in = DataModelGenerator.class.getClassLoader().getResourceAsStream("rule/like.properties");
		OrderedProperties props = new OrderedProperties();
		props.load(new InputStreamReader(in, "utf-8"));
		
		boolean flag = false;
		String columnName = model.getColumnName();
		
		for(Object key : props.keySet()) {
			if(columnName.indexOf(String.valueOf(key)) != -1) {
				String value = props.getProperty(String.valueOf(key)).replaceAll("%tableName%", tableName);	
				String[] arr = value.split("&");
				generatorEle.addAttribute("type", "com.songzhi.generate."+ arr[0]);
				if(arr.length > 1) {
					for(int i=1;i<arr.length;i++) {
						String[] nameAndValue = arr[i].split("=");
						generatorEle.addElement("property").addAttribute(nameAndValue[0],  nameAndValue[1]); 
					}
				}
				flag = true;
				break;
			}
		}
		
		return flag;
	}
	

	private  boolean relationRule(Element generatorEle,ColumnModel model, String type) throws Exception {
		
		InputStream in = DataModelGenerator.class.getClassLoader().getResourceAsStream("rule/relation.properties");
		OrderedProperties props = new OrderedProperties();
		props.load(new InputStreamReader(in, "utf-8"));

		String columnName = model.getColumnName();	//列名
		
		boolean flag = true;
		if(props.containsKey(columnName)) {
			String[] arr = props.getProperty(columnName).split("&");
			generatorEle.addAttribute("type", "com.songzhi.generate.RelationGenerator");
			if(arr.length == 2) { // PAT_MASTER_INDEX & AUTHORITY_ID //简单关联
				generatorEle.addElement("property").addAttribute("tableName", arr[0]); //关联表
				generatorEle.addElement("property").addAttribute("columnName", arr[1]); //关联字段 
			}else {//  2 & OUTP_BILL_MASTER & RECEIPT_NUMBER & 3 & INP_BILL_MASTER & RECEIPT_NUMBER	//关联
				for(int i=0;i<arr.length;i=i+3) {
					if(type.equals(arr[i])) {
						generatorEle.addElement("property").addAttribute("tableName", arr[i+1]); //关联表
						generatorEle.addElement("property").addAttribute("columnName", arr[i+2]); //关联字段 
						break;
					}
				}
			}
		}else {
			flag = false;
		}
		in.close();		
		return flag;
	}
	
	
	/** 列名生成规则    */
	private  boolean columnDictGenRule(Element generatorEle,ColumnModel model) throws Exception{
		boolean flag = false;

		String columnName = model.getColumnName();	//列名
		String[]	rules = new String[]{"file_dict.properties", "db_dict.properties", "enum.properties"};
		for(int i=0;i<rules.length;i++) {
			InputStream in = DataModelGenerator.class.getClassLoader().getResourceAsStream("rule/" + rules[i]);
			OrderedProperties props = new OrderedProperties();
			props.load(new InputStreamReader(in, "utf-8")); 
			String generatorClass = i==0? "DictionaryFromFileGenerator" : (i==1? "DictionaryFromDBGenerator" : "DictionaryFromDataGenerator");
			generatorEle.addAttribute("type", "com.songzhi.generate." + generatorClass);
			if(props.containsKey(columnName)) {
				String[] arr = props.getProperty(columnName).split("&");	//dictType=1&classCode=WS/TXXX-2009CV5502.20
				for(int j=0;j<arr.length;j++) {
					String[] nameAndValue = arr[j].split("=");
					generatorEle.addElement("property").addAttribute(nameAndValue[0],  nameAndValue[1]); 
				}
				flag = true;
				break;
			}
			in.close();
		}
		return flag;
	}
	
	/** 列类型生成策略*/
	private  boolean columnTypeGenRule(Element generatorEle, ColumnModel model) {
		boolean flag = true;
//		String columnName = model.getColumnName();	//列名
		int dataLength = model.getDataLength();	//类型长度
//		String nullable = model.getNullable() == 'N' ? "0":"1";	//是否为空 How many nulls should this generator produce per every 100 generations? [0 - 100, default: 0]
		int dataPrecision = model.getDataPrecision();	//数字精度
		int dataScale = model.getDataScale();	//数字范围
		String dataType	=	model.getDataType();	//类型
		if("string".equals(dataType)) {
			generatorEle.addAttribute("type", "com.songzhi.generate.StringGenerator");
			generatorEle.addElement("property").addAttribute("maxLength", dataLength+"");
		}else if("integer".equals(dataType) || "short".equals(dataType) || "long".equals(dataType)) {
			int length = dataPrecision==0? dataLength : dataPrecision;
			generatorEle.addAttribute("type", "com.songzhi.generate.NumberGenerator");
			generatorEle.addElement("property").addAttribute("maxValue", getMaxValue(length));
			generatorEle.addElement("property").addAttribute("returnedType", dataType);//返回类型
		} else if("float".equals(dataType) || "double".equals(dataType) || "numeric".equals(dataType)) {
			generatorEle.addAttribute("type", "com.songzhi.generate.NumberGenerator");
			generatorEle.addElement("property").addAttribute("maxValue", getMaxValue(dataPrecision, dataScale));
			generatorEle.addElement("property").addAttribute("scale", dataScale+"");//返回类型
			generatorEle.addElement("property").addAttribute("returnedType", dataType);//返回类型
		} else if("date".equals(dataType)) {
			generatorEle.addAttribute("type", "com.songzhi.generate.DateGenerator");
			generatorEle.addElement("property").addAttribute("beginDate", "2014-01-01");
			generatorEle.addElement("property").addAttribute("endDate", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		}else {
			flag = false;
		}
		return flag;
	}

	
	/**
	 * 浮点数最大值
	 */
	private  String getMaxValue(int dataPrecision, int dataScale) {
		String value = "0";
		for(int i=0;i< dataPrecision - dataScale;i++) {
			if("0".equals(value)) value = "9";
			else value += "9";
		}
		value += ".";
		for(int i=0;i< dataScale;i++) {
			if("0".equals(value)) value = "9";
			else value += "9";
		}
		return value;
	}

	/** 整数最大值 */
	public   String getMaxValue(int length) {
		String value = "0";
		for(int i=0;i< length;i++) {
			if("0".equals(value)) value = "9";
			else value += "9";
		}
		return value;
	}
	
	public String getMaxValue(String dataType, String dataLength, String dataPrecision, String dataScale) {
		String maxvalue = "0";
		for(int i=0;i< Integer.parseInt(dataLength);i++) {
			if("0".equals(maxvalue)) maxvalue = "9";
			else maxvalue += "9";
		}
		return maxvalue;
	}
	
}
