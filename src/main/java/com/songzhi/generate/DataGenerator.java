package com.songzhi.generate;

import com.songzhi.model.TableModel;

/**
 * 数据生成策略
 */
public interface DataGenerator<T> {

	/** 
	 * 生成数据
	 * @param tableModel 数据模型
	 * @return
	 */
	public T generator(TableModel tableModel);
	
	/** 
	 * 初始化组件
	 * 	载入容器
	 * @param container
	 */
	public void initialize(Container container);
}
