package com.songzhi.generate;

import org.apache.commons.lang.math.RandomUtils;

import com.songzhi.model.TableModel;

public class FreeGenerator  extends DataGeneratorAdapter  {

	@Override
	public Object generator(TableModel tableModel) {
		return RandomUtils.nextInt(1000);
	}
	
}
