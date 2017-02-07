package org.tom.util;


public class MybatisBean{
	
	/** sql */
	private String sqlName;
	private String chOut;
	private TYPE type=TYPE.STRING;
	
	public MybatisBean(String sqlName,String chOut) {
		this.chOut = chOut;
		this.sqlName = sqlName;
	}

	public String getSqlName() {
		return sqlName;
		
	}

	public String getChOut() {
		return chOut;
	}

	public TYPE getType() {
		return type;
	}

	public MybatisBean setType(TYPE type) {
		this.type = type;
		return this;
	}





	public enum TYPE{
		INT,
		STRING,
		INTEGER,
		LONG,
		BIGDECIMAL;
		
		@Override
		public String toString() {
			switch (this) {
			case INT:
				return "int";
			case STRING:
				return "String";
			case INTEGER:
				return "Integer";
			case LONG:
				return "long";
			case BIGDECIMAL:
				return "BigDecimal";
			default :
				return "String";
			}
		}

	}
}