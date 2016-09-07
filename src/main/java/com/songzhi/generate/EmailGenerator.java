package com.songzhi.generate;

import com.songzhi.model.TableModel;
import com.songzhi.utils.RandomValue;

public class EmailGenerator extends DataGeneratorAdapter {

	@Override
	public String generator(TableModel tableModel) {
		String value = RandomValue.getEmail(10, 20);
		return value;
	}

}
