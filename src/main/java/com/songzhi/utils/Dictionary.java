package com.songzhi.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

/**
 * 字典工具
 */
public class Dictionary {
	private static Logger log = Logger.getLogger(Dictionary.class);

	private String name; // 字典名
	private Random random;
	private List<Entry> items = new ArrayList<Entry>(); // 字典值

	private int nextUniqueIndex = -1; // 下一个的索引

	/** 随机的字典代码 */
	public String randomKey() {
		return items.get(random.nextInt(items.size())).key;
	}
	
	/** 随机的字典值 */
	public String randomValue() {
		return items.get(random.nextInt(items.size())).value;
	}

	/** 随机的字典键值对 */
	public Entry randomEntry() {
		return items.get(random.nextInt(items.size()));
	}
	
	/** 随机的下一个唯一字典代码 */
	public String nextUniqueKey() {
		boolean flag = setNextUnique();
		if(flag) {
			return items.get(nextUniqueIndex).key;
		} else{
			return null;
		}
	}
	
	/** 随机的下一个唯一字典值 */
	public String nextUniqueValue() {
		boolean flag = setNextUnique();
		if(flag) {
			return items.get(nextUniqueIndex).value;
		} else{
			return null;
		}
	}
	
	/** 随机的下一个唯一字典值 */
	public Entry nextUniqueEntry() {
		boolean flag = setNextUnique();
		if(flag) {
			return items.get(nextUniqueIndex);
		} else{
			return null;
		} 
	}

	/** 字典重置 */
	public void reset() {
		nextUniqueIndex = -1;
	}

	/** 随机的下一个唯一值的索引 */
	private boolean setNextUnique() {
		boolean flag = true;
		if(items.size() > 0 && nextUniqueIndex < items.size() - 1 ){
			nextUniqueIndex++;
		}else {
			flag = false;
			log.info("No more unique values in dictionary: " + name);
		}
		return flag;
	}
	
	/** 根据值获取键*/
	public String getKeyByValue(String value) {
		String key = "";
		for(Entry entry : items) {
			if(entry.value.equals(value)) {
				key = entry.key;
				break;
			}
		}
		return key;
	}
	
	/** 根据键获取值*/
	public String getValueByKey(String key) {
		String value = "";
		for(Entry entry : items) {
			if(entry.key.equals(key)) {
				value = entry.value;
				break;
			}
		}
		return value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRandom(Random random) {
		this.random = random;
	}

	public void addItem(String key, String value) {
		items.add(new Entry(key, value));
	}
	
	public void addItem(String value) {
		items.add(new Entry("", value));
	}
	
	/** 将集合数据解析成一个字典对象 */
	public static Dictionary parserDict(List<?> list) {
		Dictionary dictionary = new Dictionary();
		dictionary.setRandom(new Random());
		for(Object obj : list) {
			Object[] arr = (Object[]) obj;
			dictionary.addItem(arr[0].toString(), arr[1].toString());
		}
		return dictionary;
	}
	
	public class Entry {
		public String key;
		public String value;
		public Entry(String key, String value) {
			super();
			this.key = key;
			this.value = value;
		}
		
		@Override
		public String toString() {
			return "[" + key + "," + value + "]";
		}
	}
}
