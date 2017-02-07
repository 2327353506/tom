package org.tom.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class Test {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws TemplateException 
	 */
	public static void main(String[] args) throws IOException, TemplateException {
		Test t =new Test();
		t.generateJavaProject("D://", "Test");
	}
	
	/**
	 * 
	 * @param folder文件夹路径
	 * @param file 文件名
	 * @throws IOException 
	 * @throws TemplateException 
	 */
	public void generateJavaProject(String folder ,String fileNme) throws IOException, TemplateException{
		File f = new File(folder);
		if(f.exists()){
			if(!f.isDirectory()){
				throw new RuntimeException("不是一个文件夹");
			}
		}else{
			if(!(f.mkdirs()&&folder.indexOf(".")<0)){
				throw new RuntimeException("不是一个文件夹");
			}
		}
		File javaProject = null;
		if(fileNme.indexOf(".")>=0){
			javaProject = new File(folder+"/"+fileNme);
		}else{
			javaProject = new File(folder+"/"+fileNme+".xml");
		}
		if(!javaProject.exists()){
			javaProject.createNewFile();
		}
		Configuration config = new Configuration();
		Template t = config.getTemplate("/templates/sqlMapper.ftl");
		FileWriter fw =new FileWriter(javaProject);
		Writer out = new BufferedWriter(fw);
		Map<String, String> map = new HashMap<>();
		map.put("package", "emro.model");
		map.put("column", "name"); 
		map.put("tableName", fileNme);
		t.process(map, out);
	}

}
