package com.github.platform.sf.core.mybatis.page.dialect;


import com.github.platform.sf.core.mybatis.page.Page;

public class Db2Dialect extends AbstractDialect {

	@Override
	public String getPageSql(String sql, Page<Object> page) {
		StringBuilder sqlBuilder = new StringBuilder(sql.length() + 120);
		int startRow = (page.getPageNo() - 1) * page.getPageSize();
		int endRow = startRow + page.getPageSize();
		sqlBuilder.append("SELECT * FROM (SELECT TMP_PAGE.*,ROWNUMBER() OVER() AS ROW_ID FROM ( ");
		sqlBuilder.append(sql);
		sqlBuilder.append(" ) AS TMP_PAGE) TMP_PAGE WHERE ROW_ID BETWEEN ");
		sqlBuilder.append(startRow + 1);
		sqlBuilder.append(" AND ");
		sqlBuilder.append(endRow);
		return sqlBuilder.toString();
	}

}
