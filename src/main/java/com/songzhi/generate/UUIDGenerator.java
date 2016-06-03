package com.songzhi.generate;

import java.util.UUID;

import com.songzhi.model.TableModel;

public class UUIDGenerator extends DataGeneratorAdapter {

	public static void main(String[] args) {
		for(int i=0;i<100;i++) {
  	System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));
	}
	}

	@Override
	public String generator(TableModel tableModel) {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
}
