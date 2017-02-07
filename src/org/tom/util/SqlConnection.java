package org.tom.util;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 
 * @author wmt
 *	@Date 2016-11-25
 *	@description 数据连接类
 */

public class SqlConnection {
	
	private String url;
	private String user;
	private String password;
	private String drive;
	private String tableSpace;
	private Connection con;
	private Statement statement;

	/**
	 * 初始化参数
	 *	@author wmt
	 *	@Date 2016-11-25
	 *	@description 
	 * @param url 数据库连接地址
	 * @param user 数据库用户名
	 * @param password 数据库密码
	 * @param drive 驱动
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public SqlConnection(String url, String user, String password, String drive) throws SQLException, ClassNotFoundException {
		this.url = url;
		this.user = user;
		this.password = password;
		this.drive = drive;
		Class.forName(drive);
		con = DriverManager.getConnection(url, user, password);
		statement = con.createStatement();
	}
	/**
	 * 初始化参数
	 *	@author wmt
	 *	@Date 2016-11-25
	 *	@description 
	 * @param url 数据库连接地址
	 * @param user 数据库用户名
	 * @param password 数据库密码
	 * @param drive 驱动
	 * @param tableSpace 表空间
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public SqlConnection(String url, String user, String password, String drive,String tableSpace) throws SQLException, ClassNotFoundException {
		this.url = url;
		this.user = user;
		this.password = password;
		this.drive = drive;
		this.tableSpace = tableSpace;
		Class.forName(drive);
		con = DriverManager.getConnection(url, user, password);
		statement = con.createStatement();
	}
	

	/**
	 * 
	 *	@author wmt
	 *	@Date 2016-11-25
	 *	@description 执行sql语句
	 * @param sql
	 * @return 内容结果集
	 * @throws SQLException
	 */
	public List<Map<String, Object>> doSql(String sql) throws SQLException{
		ResultSet result = statement.executeQuery(sql);
		List<Map<String, Object>> data = new ArrayList<>();
		while(result.next()){
			ResultSetMetaData metaData = result.getMetaData();
			int row = metaData.getColumnCount();
			Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 1; i <= row; i++) {
				map.put(metaData.getColumnLabel(i), result.getObject(i));
			}
			data.add(map);
		}
		close();
		return data;
	}
	



	/**
	 * 
	 *	@author wmt
	 *	@Date 2016-11-25
	 *	@description 执行sql并转换成对应类
	 * @param sql 执行的SQL语句
	 * @param pmType 转换的类型
	 * @return 输出的结果集
	 * @throws Exception
	 */
	public <T> List<T> doSql(String sql, Class<T> pmType) throws Exception {
		List<Map<String, Object>> list = this.doSql(sql);
		List<T>  result = new ArrayList<>(); 
		for (Map<String, Object> map : list) {
			T param = pmType.newInstance();
			for (Entry<String, Object> entity : map.entrySet()) {
				String mName = "set"+entity.getKey().substring(0, 1).toUpperCase() + entity.getKey().substring(1);
				Method[] methods = param.getClass().getMethods();
				for (Method method : methods) {
					if(mName.equals(method.getName())){
						String ss= method.getParameterTypes().getClass().getName();//TODO
						try {
							method.invoke(param, entity.getValue());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
			result.add(param);
		}
		close();
		return result;
	}
	/**
	 *	@author wmt
	 *	@Date 2016-12-6
	 *	@description 关闭流
	 */
	private void close() {
		if(statement !=null){
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(this.con!=null){
			try {
				this.con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
}
