package com.songzhi.utils;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
//	  pathname->match(regex, pathname.getName())
	  
	  List<File> array = new ArrayList<File>();

	  File[] files = root.listFiles(f->match(regex, f.getName()));
	  
	  array.addAll(Arrays.asList(files));
	  
	  File[] dirs = root.listFiles(f-> f.isDirectory() && !f.isHidden());
	  for(File dir : dirs) {
	  	File[] fs = search(regex, dir.getAbsolutePath().replaceAll("%20"," ") );
	  	array.addAll(Arrays.asList(fs));
	  }
	  
		return array.toArray(new File[]{});
	}
	
	public static File[] search(String regex) {
		return search(regex, new File("").getAbsolutePath().replaceAll("%20"," "));
	}
	
	/**搜索XML文件*/
	public static File searchXml(String filename) {
		File[] files = search("*" + filename + ".xml");
		return files != null ? files[0] : null;
	}
	
	/**
   * 通配符算法。 可以匹配"*"和"?"
   * 如a*b?d可以匹配aAAAbcd
   * @param pattern 匹配表达式
   * @param str 匹配的字符串
   * @return
   */
  public static boolean match(String pattern, String str) {
      if (pattern == null || str == null)
          return false;

      boolean result = false;
      char c; // 当前要匹配的字符串
      boolean beforeStar = false; // 是否遇到通配符*
      int back_i = 0;// 回溯,当遇到通配符时,匹配不成功则回溯
      int back_j = 0;
      int i, j;
      for (i = 0, j = 0; i < str.length();) {
          if (pattern.length() <= j) {
              if (back_i != 0) {// 有通配符,但是匹配未成功,回溯
                  beforeStar = true;
                  i = back_i;
                  j = back_j;
                  back_i = 0;
                  back_j = 0;
                  continue;
              }
              break;
          }

          if ((c = pattern.charAt(j)) == '*') {
              if (j == pattern.length() - 1) {// 通配符已经在末尾,返回true
                  result = true;
                  break;
              }
              beforeStar = true;
              j++;
              continue;
          }

          if (beforeStar) {
              if (str.charAt(i) == c) {
                  beforeStar = false;
                  back_i = i + 1;
                  back_j = j;
                  j++;
              }
          } else {
              if (c != '?' && c != str.charAt(i)) {
                  result = false;
                  if (back_i != 0) {// 有通配符,但是匹配未成功,回溯
                      beforeStar = true;
                      i = back_i;
                      j = back_j;
                      back_i = 0;
                      back_j = 0;
                      continue;
                  }
                  break;
              }
              j++;
          }
          i++;
      }

      if (i == str.length() && j == pattern.length())// 全部遍历完毕
          result = true;
      return result;
  }
	
	public static void main(String[] args) { 
		File[] files = search("*INPATIENT_ORDERS.xml");
//		File[] files = new File(file.getAbsolutePath()).listFiles();
		for(File f : files) {
			System.out.println(f.getAbsolutePath());
		}
	}

}
