package org.tom.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.tom.model.TableColumn;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class JavaBeanCreate {
	
	private String sql = "select "+
			"column_name coName,"+
			"data_type coType,"+
			"column_comment coComment "+
			"from information_schema.columns "+
			"where table_schema = '${tableSpace}' and table_name='${tableName}' "+
			"ORDER BY ordinal_position";
	
	private String url;
	private String user;
	private String password;
	private String drive;
	private String tableSpase;
	
	void init(){
		Properties pop = new Properties();
		InputStream in = Object.class.getResourceAsStream("/config.properties");
		if(in!=null){
			try {
				pop.load(in);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			throw new RuntimeException("config.properties文件不存在");
		}
		this.url = pop.getProperty("url");
		this.user = pop.getProperty("user");
		this.password = pop.getProperty("password");
		this.drive = pop.getProperty("drive");
		this.tableSpase = pop.getProperty("tableSpase");
	}
	
	
	public  void createJavaBean(String tableName) throws Exception{
		if(tableName==null || "".equals(tableName.trim())){
			throw new RuntimeException("表名不存在");
		}
		this.init();
		SqlConnection conn = new SqlConnection(url, user, password, drive);
		sql = sql.replace("${tableSpace}", tableSpase)
						.replace("${tableName}", tableName);
		List<TableColumn> list =   conn.doSql(sql,TableColumn.class);
		Set<String> set = new HashSet<>();
		for (TableColumn tableColumn : list) {
			set.add(tableColumn.getJavaType());
		}
		Map<String, Object> map = new HashMap<>();
		map.put("coList", list);
		map.put("typeList", set);
		map.put("tableName", tableName);
		map.put("fileName", getFileName(tableName));
		templateToCreate(map);
	}
	
	private void templateToCreate(Map<String, Object> map) throws Exception {
		Configuration config = new Configuration();
		String fileName = String.valueOf(map.get("fileName"));
		Template t = config.getTemplate("/templates/javaBean.ftl");
		FileWriter fw =new FileWriter("d://"+fileName+".java");
		Writer out = new BufferedWriter(fw);
		t.process(map, out);		
	}


	private String getFileName(String name){
		if(name.indexOf("_")>=0){
			String[] names= name.split("_");
			StringBuffer buff = new StringBuffer(names[0]);
			for (int i = 1,len = names.length; i < len; i++) {
				buff.append(names[i].substring(0, 1).toUpperCase()+names[i].substring(1));
			}
			return buff.toString();
		}else{
			return name;
		}
	}
	
}
