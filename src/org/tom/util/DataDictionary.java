package org.tom.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Writer;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.tom.model.TableBase;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class DataDictionary {
	
	
	private String url;
	private String user;
	private String password;
	private String drive;
	
	private final String sql = "SELECT table_name tableName,table_comment tableComment FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'vipmro' and table_type = 'BASE TABLE'";
	
	
	private Connection con = null;
	private PreparedStatement statement = null;
	
	
	
	public DataDictionary(String url, String user, String password, String drive) {
		this.url = url;
		this.user = user;
		this.password = password;
		this.drive = drive;
	}


	public void createAllTable(String path){
		try {
			SqlConnection conn = new  SqlConnection(url, user, password, drive);
			List<TableBase> TableBaseList = conn.doSql(sql, TableBase.class);
			Map<String, String> data = new  LinkedHashMap<>(TableBaseList.size());
			Map<String, List<ColunmDictionary>> colum =new LinkedHashMap<>();
			for (TableBase tableBase : TableBaseList) {
				data.put(tableBase.getTableName(), tableBase.getTableComment());
			}
			for (String str : data.keySet()) {
				List<ColunmDictionary> coList = conn.doSql(this.toSql(str,"vipmro"), ColunmDictionary.class);
				colum.put(str, coList);
			}
			Configuration config = new Configuration();
			Template t = config.getTemplate("/templates/dictionary.ftl");
			FileWriter fw =new FileWriter(path+"/dictionary.html");
			Writer out = new BufferedWriter(fw);
			Map<String, Object> map = new HashMap<>();
			map.put("data", data);
			t.process(map, out);
			Template template = config.getTemplate("/templates/colunm.ftl");
			for (Entry<String, List<ColunmDictionary>> entity : colum.entrySet()) {
				Map<String, Object>  co = new HashMap<>();
				co.put("tableName", entity.getKey());
				co.put("tableColunm", entity.getValue());
				FileWriter w =new FileWriter(path+"/page/"+entity.getKey()+".html");
				Writer o = new BufferedWriter(w);
				template.process(co, o);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			close();
		}
	}
	
	
	private String toSql(String tableName, String nameSpace) {
		// TODO Auto-generated method stub
		return "select COLUMN_NAME columnName,COLUMN_TYPE columnType,COLUMN_COMMENT columnComment,COLUMN_DEFAULT columnDefault,if(IS_NULLABLE='YES',0,1)isNullable from information_schema.COLUMNS where table_name = '"+tableName+"' and TABLE_SCHEMA = '"+nameSpace+"'";
	}


	private void close() {
		if(this.con != null){
			try {
				this.con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(this.statement != null){
			try {
				this.statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDrive() {
		return drive;
	}
	public void setDrive(String drive) {
		this.drive = drive;
	}
	
	public static void main(String[] args) {
		String url = "jdbc:mysql://192.168.1.211:3306/vipmro?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";
		String usr = "root";
		String password = "root";
		String drive = "com.mysql.jdbc.Driver";
		DataDictionary data = new DataDictionary(url, usr, password, drive);
		data.createAllTable("D:/");
	}

}
