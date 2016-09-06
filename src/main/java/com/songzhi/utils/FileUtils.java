package com.songzhi.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.songzhi.generate.Container;

/**
 * 文件工具类
 * @author songzhi
 *
 */
public class FileUtils {

	/** 
	 * 根据文件名,搜索文件 
	 * @param regex 正则表达式
	 * @param baseDir 搜索文件根目录
	 * @return
	 */
	public static File[] search(String  regex, String baseDir) {
	  File root = new File(baseDir); 
	  
	  List<File> array = new ArrayList<File>();

	  File[] files = root.listFiles(f->f.getName().endsWith(regex));
	  
	  array.addAll(Arrays.asList(files));
	  
	  File[] dirs = root.listFiles(f-> f.isDirectory() && !f.isHidden());
	  for(File dir : dirs) {
	  	File[] fs = search(regex, dir.getAbsolutePath().replaceAll("%20"," ") );
	  	array.addAll(Arrays.asList(fs));
	  }
	  
		return array.toArray(new File[]{});
	}
	
	public static File[] search(String regex) {
		return search(regex, Container.RESOURCES_FOLDER);
	}
	
	/**搜索XML文件*/
	public static File searchXml(String filename) {
		File[] files = search(filename + ".xml");
		return files != null ? files[0] : null;
	}
	
	public static void main(String[] args) { 
		File[] files = search("", "*INPATIENT_ORDERS.xml");
//		File[] files = new File(file.getAbsolutePath()).listFiles();
		for(File f : files) {
			System.out.println(f.getAbsolutePath());
		}
	}

}
