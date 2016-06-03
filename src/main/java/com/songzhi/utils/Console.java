package com.songzhi.utils;

import java.io.InputStream;
import java.lang.reflect.Field;

import com.songzhi.factory.DataModelGenerator;

public class Console {

	public static void main(String[] args) throws Exception {
		/*Class<?> clazz = DictConstants.class;
		Object obj = clazz.newInstance();
		Field[] fields = clazz.getDeclaredFields();
		for(Field field : fields) {
			String value = String.valueOf(field.get(obj));
			if(value.startsWith("D#")) {
				value = value.split("#")[1];
				
				
				System.out.println(field.getName() + "=" +value);
			}
		}*/
		
		InputStream in = Console.class.getClassLoader().getResourceAsStream("rule/specify.properties");
		OrderedProperties props = new OrderedProperties();
		props.load(in);
		
		for(Object key : props.keySet()) {
			System.out.println(key + ":" + props.get(key));
		}
		
	}
}
