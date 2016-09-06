package com.songzhi.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.lang.StringUtils;

import com.songzhi.generate.Container;
import com.songzhi.utils.db.DBHelper;

/***
 * 字典管理器
 */
public class DictionaryManager {
	private static Map<String, Dictionary> dictionaries = new HashMap<String, Dictionary>();
	private static Random random = new Random();
	
	/** 获取字典对象 */
	public static Dictionary getDictionary(String dictName) throws Exception {
		if(!dictionaries.containsKey(dictName)) 
			loadDictionary(dictName);
		return dictionaries.get(dictName);
	}
	
	/** 获取字典对象 */
	public static Dictionary getDictionary(String tableName, String dictCode, String dictValue, String condition) throws Exception {
		if(!dictionaries.containsKey(tableName)) 
			loadDictionaryFromDB(tableName, dictCode, dictValue, condition);
		return dictionaries.get(tableName);
	}
	
	public static Dictionary getDictionaryFromClassCode(String classCode) throws Exception {
		if(!dictionaries.containsKey(classCode)) 
			loadDictionaryFromCharDict(classCode);
		return dictionaries.get(classCode);
	}

	/** 加载字典 */
	public static void loadDictionary(String dictName) throws Exception {
//		String path = DictionaryManager.class.getClassLoader().getResource("dict").getPath().replaceAll("%20"," ") + "/" + dictName;
		
		String path = Container.RESOURCES_FOLDER + "dict/" + dictName;
		
		InputStream is = null;
		if(new File(path+".zip").exists() && new File(path+".zip").canRead()) {
			ZipFile zf = new ZipFile(path+".zip");
			ZipEntry ze = zf.entries().nextElement();
			is = zf.getInputStream(ze);
		}else if(new File(path+".gz").exists() && new File(path+".gz").canRead()) {
			is = new GZIPInputStream(new FileInputStream(path+".gz"));
		}else {
			is = new FileInputStream(path+".txt");
		}
		
		readDictory(dictName, is);
		
		if(is != null) is.close();
	}
	
	/**
	 * 从数据库中加载字典
	 * 	<br/> 现在替换成loadDictionaryFromDB(String tableName, String dictCode, String dictValue, String condition)
	 * @param tableName	表明
	 * @param dictCode	代码字段
	 * @param dictValue	值字段
	 * 
	 */
	@Deprecated
	public static void loadDictionaryFromDB(String tableName, String dictCode, String dictValue) throws Exception {
		loadDictionaryFromDB(tableName, dictCode, dictValue, null);
	}
	/**
	 * 
	 * 从数据库中加载字典
	 * @param tableName	表明
	 * @param dictCode	代码字段
	 * @param dictValue	值字段
	 * @param condition	条件
	 */
	public static void loadDictionaryFromDB(String tableName, String dictCode, String dictValue, String condition) throws Exception {
		String sql = "select " + dictCode +"," + dictValue +" from " + tableName;
		if(StringUtils.isNotBlank(condition)) {
			sql += " where " + condition;
		}
		List<?> list = DBHelper.query(sql);
		
		Dictionary dictionary = new Dictionary();
		dictionary.setName(tableName);
		dictionary.setRandom(random);
		for(Object obj : list) {
			Object[] arr = (Object[]) obj;
			dictionary.addItem(arr[0].toString(), arr[1].toString());
		}
		dictionaries.put(tableName, dictionary);
	}
	
	/**
	 * 从COMM_DICT_PUBLIC_CHAR 中查找字典数据
	 * @param key	class_code
	 * @param condition	条件
	 * @throws Exception
	 */
	public static void loadDictionaryFromCharDict(String classCode) throws Exception {
		String sql = "select  dict_code ,dict_value from COMM.COMM_DICT_PUBLIC_CHAR WHERE CLASS_CODE='" +classCode +"'";
		List<?> list = DBHelper.query(sql);
		
		Dictionary dictionary = new Dictionary();
		dictionary.setName(classCode);
		dictionary.setRandom(random);
		for(Object obj : list) {
			Object[] arr = (Object[]) obj;
			dictionary.addItem(arr[0].toString(), arr[1].toString());
		}
		dictionaries.put(classCode, dictionary);
	}

	private static void readDictory(String dictName, InputStream is) throws Exception {
		LineNumberReader reader = new LineNumberReader(new InputStreamReader(is, "UTF-8"));
		String line = null;
		
		Dictionary dictionary = new Dictionary();
		dictionary.setName(dictName);
		dictionary.setRandom(random);
		while((line=reader.readLine()) != null) {
			if(line.indexOf("=") != -1) {
				String[] arr = line.split("=");
				dictionary.addItem(arr[0], arr[1]);
			}else {
				dictionary.addItem(line);
			}
		}
		dictionaries.put(dictName, dictionary);
	}
	
	public static void main(String[] args) throws Exception {
		Dictionary dictionary = getDictionary("country");
		System.out.println(dictionary.randomValue());
	}
}
