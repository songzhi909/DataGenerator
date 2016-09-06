package com.songzhi.generate;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.songzhi.model.TableModel;
import com.songzhi.utils.db.DBHelper;

/**
 * 序号生成器
 */
public class SerialGenerator extends DataGeneratorAdapter  {
	private static Logger log = Logger.getLogger(SerialGenerator.class);
	
	/**PID前缀 日期的yyyyMMdd格式*/
	private String prefix = new SimpleDateFormat("yyyyMMdd").format(new Date());	
	
	private static int i = 1;	//当没有序列的时候使用默认序号
	
	private int size = 8;	//序号长度 默认长度8

	@Override
	public String generator(TableModel tableModel) {
			String key = prefix;
			String index = null;
			try {
				index = String.valueOf(DBHelper.get("select PID_SEQUENCE.nextval from dual "));
			} catch (SQLException e) {
				log.error(e.getMessage());
				index = String.valueOf(i++);
			}
			return key + StringUtils.leftPad(index, 8, '0');
		 
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	public static void main(String[] args) {
		SerialGenerator gen = new SerialGenerator();
		gen.initialize(null);
//		System.out.println(gen.generator());
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
}
