package org.tom.util;

import java.sql.Connection;
import java.sql.PreparedStatement;


public class ColunmDictionary {
	
	private String columnName;
	private String columnType;
	private String columnComment;
	private String columnDefault;
	private String isNullable;
	
	private Connection con = null;
	private PreparedStatement statement = null;
	
	
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getColumnType() {
		return columnType;
	}
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	public String getColumnComment() {
		return columnComment;
	}
	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}
	public String getColumnDefault() {
		if(columnDefault==null){
			return "NULL";
		}
		return columnDefault;
	}
	public void setColumnDefault(String columnDefault) {
		this.columnDefault = columnDefault;
	}
	public String getIsNullable() {
		return isNullable;
	}
	public void setIsNullable(String isNullable) {
		this.isNullable = isNullable;
	}
	public String toSql(String tableName, String nameSpace) {
		return "select COLUMN_NAME columnName,COLUMN_TYPE columnType,COLUMN_COMMENT columnComment,COLUMN_DEFAULT columnDefault,if(IS_NULLABLE='YES',0,1)isNullable from information_schema.COLUMNS where table_name = '"+tableName+"' and TABLE_SCHEMA = '"+nameSpace+"'";
	}
	@Override
	public String toString() {
		return "ColunmDictionary [columnName=" + columnName + ", columnType="
				+ columnType + ", columnComment=" + columnComment
				+ ", columnDefault=" + columnDefault + ", isNullable="
				+ isNullable + "]";
	}
	
	
	
}
