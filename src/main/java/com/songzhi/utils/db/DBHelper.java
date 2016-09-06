package com.songzhi.utils.db;

import java.beans.PropertyVetoException;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.songzhi.model.DatabaseListWrapper;
import com.songzhi.model.DatabaseModel;

/**
 * 数据库工具类
 * @author songzhi
 *
 */
public class DBHelper {
	private static final Logger log = Logger.getLogger(DBHelper.class);
	
	private static final String DATABSE_FILE = "db.xml"; 

	public static ComboPooledDataSource dataSource;
	
	static {
		try {
			setDatabase(defaultDatabase());
			dataSource = new ComboPooledDataSource("cehrp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static Connection getConnection() {
		Connection conn = null;
		if (null != dataSource) {
			try {
				conn = dataSource.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return conn;
	}

	/**
	 * 查找多个对象
	 * 
	 * @param sqlString
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public static List query(String sqlString, Class clazz) {
		List beans = null;
		Connection conn = null;
		try {
			conn = getConnection();
			QueryRunner qRunner = new QueryRunner();
			beans = (List) qRunner.query(conn, sqlString, new BeanListHandler(
					clazz));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(conn);
		}
		return beans;
	}

	@SuppressWarnings({ "unchecked" })
	public static List query(String sqlString, String... params) throws SQLException {
		List beans = null;
		Connection conn = null;
		try {
			conn = getConnection();
			QueryRunner qRunner = new QueryRunner();
			beans = qRunner.query(conn, sqlString, new ArrayListHandler(), params);
		} catch (SQLException e) {
			//e.printStackTrace();
			throw e;
		} finally {
			DbUtils.closeQuietly(conn);
		}
		return beans;
	}
	
	public static Object get(String sqlString, String... params) throws SQLException {
		List<?> beans = null;
		Object obj = null;
		Connection conn = null;
		try {
			conn = getConnection();
			QueryRunner qRunner = new QueryRunner(); 
			beans = qRunner.query(conn, sqlString, new ArrayListHandler(), params);
		} catch (SQLException e) {
			//e.printStackTrace();
			throw e;
		} finally {
			DbUtils.closeQuietly(conn);
		}
		if (beans != null && !beans.isEmpty()) { // 注意这里
			obj = ((Object[])beans.get(0))[0];
		}
		return obj;
	}

	/**
	 * 查找对象
	 * @param sqlString
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public static Object get(String sqlString, Class clazz) {
		List beans = null;
		Object obj = null;
		Connection conn = null;
		try {
			conn = getConnection();
			QueryRunner qRunner = new QueryRunner();
			beans = (List) qRunner.query(conn, sqlString, new BeanListHandler(
					clazz));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(conn);
		}
		if (beans != null && !beans.isEmpty()) { // 注意这里
			obj = beans.get(0);
		}
		return obj;
	}
	
	/** 根据表明读取表描述*/
	public static String getTableDesc(String tableName) {
		String tableDesc = tableName;
		try {
			String sql = "select NVL(COMMENTS, TABLE_NAME) from DBA_TAB_COMMENTS t where t.table_name =?";
			Object obj = get(sql, tableName);
			if(StringUtils.isNotBlank(String.valueOf(obj))) {
				tableDesc = String.valueOf(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tableDesc;
	}

	/**
	 * 执行更新的sql语句,插入,修改,删除
	 * @param sqlString
	 * @return
	 */
	public static boolean update(String sqlString) {
		Connection conn = null;
		boolean flag = false;
		try {
			conn = getConnection();
			QueryRunner qRunner = new QueryRunner();
			int i = qRunner.update(conn, sqlString);
			if (i > 0) {
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(conn);
		}
		return flag;
	}
	
	/**
	 * 批量执行
	 * @param sql
	 * @param params
	 * @return
	 */
	public static boolean batch(String sql, Object[][] params) {
		Connection conn = null;
		boolean flag = true;
		try {
			conn = getConnection();
			QueryRunner qRunner = new QueryRunner();
			int[] result = qRunner.batch(sql, params);
			for(int i=0;i<result.length;i++) {
				if(result[i] < 0 ) {
					flag = false;
					System.out.println("第" + i + "行执行有误!");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(conn);
		}
		return flag;
	}
	
	public static boolean executeSql(String sql) {
		Connection conn = null;
		boolean flag = true;
		try {
			conn = getConnection();
			Statement statement = conn.createStatement();
			flag = statement.execute(sql); 
		} catch (SQLException e) {
			log.error(e.getMessage());
			flag = false;
		} finally {
			DbUtils.closeQuietly(conn);
		}
		return flag;
	}
	
	public static boolean batch(Object[] sqls) {
		Connection conn = null;
		boolean flag = true;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			Statement statement = conn.createStatement();
			for(Object sql : sqls) {
				statement.addBatch(String.valueOf(sql));
			}
			int result[] = statement.executeBatch();
			conn.commit();
		} catch (SQLException e) {
			log.error(e.getMessage());
			flag = false;
		} finally {
			DbUtils.closeQuietly(conn);
		}
		return flag;
	}
	
	/**
	 * 将数据写入数据表中
	 * @param tableName 数据表
	 * @param list 数据
	 */
	public static void write2db(String tableName,String[] cols, Object[][] params) throws Exception {
		String[] values = new String[cols.length];
		for(int i=0;i<cols.length;i++)  values[i] = "?"; 
		
		String colStr = StringUtils.join(cols, ",");
		String valueStr = StringUtils.join(values, ",");
		
		StringBuilder sb = new StringBuilder("insert into " + tableName + "("+ colStr  +") values(" + valueStr + ")");
		System.out.println(sb.toString());
		DBHelper.batch(sb.toString(), params);
	}
	
	/** 读取数据库连接信息列表 */
	public static List<DatabaseModel> fetchDatabases() {
		// reading xml from the file and unmarshalling.
		List<DatabaseModel> databases = null;;
		try {
			JAXBContext context = JAXBContext.newInstance(DatabaseListWrapper.class);
			Unmarshaller um = context.createUnmarshaller();
			DatabaseListWrapper wrapper = (DatabaseListWrapper) um.unmarshal(new File(DATABSE_FILE));
			databases = wrapper.getDatabasess();
		} catch (JAXBException e) {
			log.error(e.getMessage());
		}
		return databases;
	}
	
	/** 默认数据库地址 */
	public static DatabaseModel defaultDatabase() {
		DatabaseModel defaultDatabase = new DatabaseModel();
		
		for(DatabaseModel databaseModel : fetchDatabases()) {
			if("1".equals(databaseModel.getDefaultFlag()))  {
				defaultDatabase = databaseModel;
				break;
			}
		}
		
		return defaultDatabase;
	}
	
	/** 设置默认数据库连接 */
	public static void setDatabase(DatabaseModel databaseModel) {
		try {
			dataSource = new ComboPooledDataSource();
			dataSource.setDriverClass("oracle.jdbc.driver.OracleDriver");
			dataSource.setJdbcUrl(databaseModel.getDbUrl());
			dataSource.setUser(databaseModel.getDbUserName());
			dataSource.setPassword(databaseModel.getDbPassword());
			dataSource.setInitialPoolSize(50);
			dataSource.setMaxPoolSize(100);
			dataSource.setMaxIdleTime(10000);
		} catch (PropertyVetoException e) {
			log.error(e.getMessage());
		}
	}
	
	/** 保存数据库连接信息 */
	public static void saveDatabases(List<DatabaseModel> databases) {
		try {
			JAXBContext context = JAXBContext.newInstance(DatabaseListWrapper.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			
			// wrapping our person data
			DatabaseListWrapper wrapper = new DatabaseListWrapper();
			wrapper.setDatabases(databases);;
			
			// Marshalling and saving xml to the file
			m.marshal(wrapper, new File(DATABSE_FILE));
		} catch (PropertyException e) {
			log.error(e.getMessage());
		} catch (JAXBException e) {
			log.error(e.getMessage());
		}
	}
	
}
