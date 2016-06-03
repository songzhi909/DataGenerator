package com.songzhi.generate;

import com.songzhi.model.TableModel;
import com.songzhi.utils.RandomValue;

public class AddressGenerator extends DataGeneratorAdapter {

	@Override
	public String generator(TableModel tableModel) {
		String value = RandomValue.getRoad();
		return value;
	}

}
