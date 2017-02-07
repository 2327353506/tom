package org.tom.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.tom.util.MybatisBean.TYPE;

public class MybatisBeanUtil {
	
	private String beanName;
	
	private String packagePath;
	
	private Map<String, MybatisBean> param = new HashMap<>();
	
	public MybatisBeanUtil(String beanName,String packagePath) {
		this.beanName = beanName;
		this.packagePath = packagePath;
		this.param = new HashMap<>();
	}

	public MybatisBeanUtil put(String key,MybatisBean value){
		this.param.put(key, value);
		return this;
	}
	public MybatisBeanUtil put(String key){
		this.param.put(key, null);
		return this;
	}
	

	public String getBeanName() {
		return beanName;
	}

	public String getPackagePath() {
		return packagePath;
	}

	public Map<String, MybatisBean> getParam() {
		return param;
	}
	
	
	public void generateBean(String path) throws Exception{
		StringBuffer top = new StringBuffer("package "+this.packagePath +";\r");
		
		StringBuffer middle = new StringBuffer("public class "+this.beanName+" {\r");
		
		for (Entry<String, MybatisBean> data : param.entrySet()) {
			middle.append("    ");
			middle.append("private "+data.getValue().getType().toString() +" "+data.getKey()+";");
			String chOut = data.getValue().getChOut();
			if(chOut !=null && !"".equals(chOut.trim())){
				middle.append("//" + chOut);
			}
			middle.append("\r");
		}
		middle.append("}");
		File file = new File(path);
		if(!file.isDirectory()){
			throw new Exception("请填写正确文件夹");
		}else{
			file = new File(path+this.beanName+".java");
		}
		FileOutputStream out = new FileOutputStream(file);
		out.write(top.toString().getBytes());
		out.write(middle.toString().getBytes());
		out.flush();
		out.close();
	}
	public void generateMybatis(){
		StringBuffer top = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\r");
		top.append("<mapper namespace="+this.packagePath+"."+this.beanName+" >");
		StringBuffer select = new StringBuffer("    <select id=\"find\" resultType=\"Map\">\r");
		select.append("        select\r");
		for (Entry<String, MybatisBean> data : param.entrySet()) {
			select.append("            " + data.getValue().getSqlName() +" "+data.getKey() + ",\r");
		}
		select.deleteCharAt(select.length()-3);
		select.append("        from "+ this.beanName +"\r");
		select.append("    </select>");
		StringBuffer bottom =new StringBuffer("</mapper>");
		System.out.println(top);
		System.out.println(select);
		System.out.println(bottom);
	}
	
	
	
	
	
	

	public static void main(String[] args) throws Exception {
		MybatisBeanUtil bean = new MybatisBeanUtil("dealerCreditFeedback", "");
		bean.put("applyCode", new MybatisBean("apply_code", "金诺内部授信申请号，可用于判重"));
		bean.put("customerCode", new MybatisBean("customer_code", "客户编码（工品汇）"));
		bean.put("customerName", new MybatisBean("customer_name", "客户名称（冗余）"));
		bean.put("lastCreditNo", new MybatisBean("last_credit_no", "上次授信单号"));
		bean.put("lastCreditStatus", new MybatisBean("last_credit_status", "上次授信状态（启用、终止）"));
		bean.put("creditStatus", new MybatisBean("credit_status", "通过/未通过"));
		bean.put("applyNote", new MybatisBean("apply_note", "未通过理由"));
		bean.put("creditNo", new MybatisBean("credit_no", "授信单号（请保存此号）"));
		bean.put("creditLimit", new MybatisBean("credit_limit", "授信额度").setType(TYPE.BIGDECIMAL));
		bean.put("currency", new MybatisBean("currency", "币种"));
		bean.put("startDate", new MybatisBean("start_date", "授信开始日期（含）"));
		bean.put("endDate", new MybatisBean("end_date", "授信结束日期（不含）"));
		bean.put("rateType", new MybatisBean("rate_type", "利率类型：1：日化；2：月化；3：年化").setType(TYPE.INT));
		bean.put("rate", new MybatisBean("rate", "利率"));
		bean.put("repaymentWay", new MybatisBean("repayment_way", "到期还本付息"));
		bean.put("updateTime", new MybatisBean("update_time", "更新时间"));
		bean.generateMybatis();
		bean.generateBean("D:/");
	}
}
