package com.songzhi.generate;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.songzhi.model.TableModel;

/**
 * 排序号生成器 从1开始
 */
public class OrderNoGenerator extends DataGeneratorAdapter {
	private static final Logger log = Logger.getLogger(OrderNoGenerator.class);
	
	private static Map<String, Integer> map = new HashMap<String, Integer>();

	private String prefix = "";	//序号前缀
	private String tableName;	//表名
	private String uniqueKey;	//唯一标识 主键字段','拼接

	@Override
	public String generator(TableModel tableModel) {
		log.debug("开始生成该业务表【" + tableModel.getTableName() + "】的序列字段，依据字段为：" + uniqueKey);
		
		StringBuffer sb = new StringBuffer(tableName);
		String[] primarys = uniqueKey.split(",");
		for(String primary : primarys) {
			if(StringUtils.isNotBlank(primary)) {
				Object primaryValue = tableModel.getColumnModel(primary).getColumnValue();
				sb.append("-").append(String.valueOf(primaryValue));
			}
		}
		String key = sb.toString();
		if(!map.containsKey(key)) {
			map.put(key, 0);
		}
		
		Integer value = map.get(key) + 1;
		map.put(key, value);

		log.debug("生成该业务表【" + tableModel.getTableName() + "】的序列字段" + value);
		return prefix + value;
	}
	
	public static Map<String, Integer> getMap() {
		return map;
	}

	public String getPrefix() {
		return prefix;
	}

	public String getTableName() {
		return tableName;
	}

	public String getUniqueKey() {
		return uniqueKey;
	}

	public static void setMap(Map<String, Integer> map) {
		OrderNoGenerator.map = map;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setUniqueKey(String uniqueKey) {
		this.uniqueKey = uniqueKey;
	}
	
}
