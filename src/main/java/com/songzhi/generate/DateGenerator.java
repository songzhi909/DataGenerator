package com.songzhi.generate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import com.songzhi.model.TableModel;

/**
 * 日期生成器
 */
public class DateGenerator extends DataGeneratorAdapter{
	private static Logger log = Logger.getLogger(DateGenerator.class);
	
	/** 开始日期 默认为前三个月*/
	private String beginDate;
	/** 结束日期 默认当天*/
	private String endDate;

	/**
	 * 生成随机时间
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static Date randomDate(String beginDate, String endDate) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date start = format.parse(beginDate);// 构造开始日期
			Date end = format.parse(endDate);// 构造结束日期

			return randomDate(start, end);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 生成随机时间
	 */
	public static Date randomDate(Date beginDate, Date endDate) {
		try {
			if (beginDate.getTime() >= endDate.getTime()) {
				return null;
			}
			long date = random(beginDate.getTime(), endDate.getTime());
			return new Date(date);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	/** 随机生成前三个月的日期  */
	public static Date randomDate() {
		Date endTime = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int month = calendar.get(Calendar.MONTH);
		calendar.set(Calendar.MONTH, month - 3);
		Date startTime = calendar.getTime();
		return randomDate(startTime, endTime);
	}

	public static long random(long begin, long end) {
		long rtn = begin + (long) (Math.random() * (end - begin));
		// 如果返回的是开始时间和结束时间，则递归调用本函数查找随机值
		if (rtn == begin || rtn == end) {
			return random(begin, end);
		}
		return rtn;

	}

	@Override
	public Date generator(TableModel tableModel) {
		if(beginDate != null && endDate != null) {
			return randomDate(beginDate, endDate);
		}
		return randomDate();
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	@Override
	public void initialize(Container container) {
		// TODO Auto-generated method stub
		
	}

}
