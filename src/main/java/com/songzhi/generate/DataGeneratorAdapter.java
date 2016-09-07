package com.songzhi.generate;

import org.apache.log4j.Logger;

import com.songzhi.model.TableModel;

public class DataGeneratorAdapter implements DataGenerator<Object> {
	private static Logger log = Logger.getLogger(DataGeneratorAdapter.class);
	
	public Container container;
	
	private int minLength;	//最大长度
	private int maxLength;	//最小长度
	private boolean nullable = true;	//是否可以为空 默认可以为空
	
	@Override
	public Object generator(TableModel tableModel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initialize(Container container) {
		log.debug("初始化生成器配置...");
		this.container = container;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public boolean isNullable() {
		return nullable;
	}
	
	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public int getMinLength() {
		return minLength;
	}

}
