package com.songzhi.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

import com.songzhi.generate.Container;
import com.songzhi.utils.db.DBHelper;

/***
 * 字典文件生成工具
 */
public class DictFileGenerator {
	private static Logger logger = Logger.getLogger(DictFileGenerator.class);

	public static void main(String[] args) throws Exception {
		/*String sql = "select 'COMM.'||table_name from dba_all_tables t where t.owner = 'COMM'";
		List<?> tableNames = DBHelper.query(sql);
		for(Object obj : tableNames) {
			Object[] arr = (Object[]) obj;
			genernateDictFile("comm", arr[0].toString());
		}*/
//		genernateDictFile("comm", "comm_ihe_authority", "universal_id", "hsp_config_baseinfo_id");
//		genernateDictFile("hsp", "hsp_config_baseinfo", "id", "item_name");
//		genernateDictFile("tenant", "tenant_baseinfo", "id", "comm_config_location_id_3");
	genernateDictFile("hsp", "hsp_dept_baseinfo", "dept_code", "dept_name");
	}
	
	public static void genernateDictFile(String owner, String tablename){
		genernateDictFile("owner", tablename, "item_name", "item_code");
	}

	/**
	 * 根据表生成字典文件
	 * @param tablename
	 */
	public static void genernateDictFile(String owner, String tablename, String... columns){
		tablename = tablename.trim().toUpperCase();
		logger.info("创建字典:" + tablename + "...");
		String sql = "select ";
		for(int i=0;i<columns.length;i++) {
			if(i==0) sql += columns[i];
			else sql += "," + columns[i];
		}
		sql +=" from " + owner + "." + tablename;
		try {
			List<?> list = DBHelper.query(sql);
			
			if(list == null || list.size() == 0) return;
			
			String filename = Container.RESOURCES_FOLDER + "dict/" + tablename + ".txt"; 
			File file = new File(filename);
			PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
			
			for(Object obj : list ){
				Object[] arr = (Object[]) obj;
				out.println(arr[0] + "=" + arr[1]);
			}
			out.flush();
			out.close();
			
			
			//压缩文件为zip格式
			File zipFile = new File(Container.RESOURCES_FOLDER + "dict/" + tablename + ".zip");
			InputStream input = new FileInputStream(filename);
			
			ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
			zipOut.putNextEntry(new ZipEntry(file.getName()));
			
			zipOut.setComment("copy from cehrp");//添加注释
			int temp;
			while((temp=input.read()) != -1) {
				zipOut.write(temp);
			}
			input.close();
			zipOut.close();
			
			file.delete();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		logger.info("创建字典:" + tablename + "完成！");
	}
}
