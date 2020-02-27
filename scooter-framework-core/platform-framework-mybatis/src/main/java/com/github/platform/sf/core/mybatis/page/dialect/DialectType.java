package com.github.platform.sf.core.mybatis.page.dialect;

public enum DialectType {

	MYSQL("mysql", MySqlDialect.class.getName()),
	ORACLE("oracle", OracleDialect.class.getName()),
	DB2("db2", Db2Dialect.class.getName());
	
	private String name;
	
	private String dialectName;

	DialectType(String name, String dialectName) {
		this.name = name;
		this.dialectName = dialectName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDialectName() {
		return dialectName;
	}

	public void setDialectName(String dialectName) {
		this.dialectName = dialectName;
	}
}
