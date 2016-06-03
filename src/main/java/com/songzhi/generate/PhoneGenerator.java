package com.songzhi.generate;

import com.songzhi.model.TableModel;
import com.songzhi.utils.RandomValue;

/**
 * 手机号生成器
 */
public class PhoneGenerator  extends DataGeneratorAdapter {

	@Override
	public String generator(TableModel tableModel) {
		String phone = RandomValue.getTel();
		return phone;
	}

}
