package org.tom.model;


public class TableColumn {
	

	
	private String coName;//字段名
	private String coType;//字段类型
	private String coComment;//字段描述
	private String coLength;//字段长度(废弃)
	
	
	public String getCoName() {
		return coName;
	}
	public void setCoName(String coName) {
		this.coName = coName;
	}
	public String getCoType() {
		return coType;
	}
	public void setCoType(String coType) {
		this.coType = coType;
	}
	public String getCoComment() {
		return coComment;
	}
	public void setCoComment(String coComment) {
		this.coComment = coComment;
	}
	public String getCoLength() {
		return coLength;
	}
	public void setCoLength(String coLength) {
		this.coLength = coLength;
	}
	
	/**
	 *	@author wmt
	 *	@Date 2016-12-6
	 *	@description 
	 * @return sql类型转换成java 类型
	 */
	public String getJavaType(){
		if(this.coType == null){
			return "java.lang.String";
		}
		switch (this.coType.toUpperCase()) {
		case "VARCHAR": case "CHAR": case "TEXT":
			return "java.lang.String";
		case "BLOB": 
			return "java.lang.byte";
		case "INTEGER": case "ID": case "BIGINT": 
			return "java.lang.Long";	
		case "TINYINT": case "SMALLINT": case "MEDIUMINT": 
			return "java.lang.Integer";	
		case "BIT": case "BOOLEAN":
			return "java.lang.Boolean";	
		case "FLOAT": case "DOUBLE": case "DECIMAL": 
			return "java.math.BigDecimal";	
		case "DATE": case "YEAR":
			return "java.sql.Date";	
		case "DATETIME": case "TIMESTAMP":
			return "java.sql.Timestamp";	
		default:
			return "java.lang.String";
		}
	}
	/**
	 *	@author wmt
	 *	@Date 2016-12-6
	 *	@description 获取类型后缀
	 * @return 
	 */
	public String getSimpleType(){
		String type= this.getJavaType();
		String[] types = type.split("\\.");
		return types[types.length-1];
	}
	/**
	 *	@author wmt
	 *	@Date 2016-12-6
	 *	@description 获取字段别名
	 * @return
	 */
	public String getAlias(){
		if(this.coName.indexOf("_")>=0){
			String[] names= this.coName.split("_");
			StringBuffer buff = new StringBuffer(names[0]);
			for (int i = 1,len = names.length; i < len; i++) {
				buff.append(names[i].substring(0, 1).toUpperCase()+names[i].substring(1));
			}
			return buff.toString();
		}else{
			return this.coName;
		}
	}
	@Override
	public String toString() {
		return "TableColumn [coName=" + coName + ", coType=" + coType
				+ ", coComment=" + coComment + ", coLength=" + coLength + ", getAlias=" + getAlias()
				+ ", getSimpleType=" + getSimpleType()+ ", getJavaType()=" + getJavaType() + "]";
	}

}
