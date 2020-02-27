package com.github.platform.sf.core.mybatis.page.interceptor;


import com.github.platform.sf.core.mybatis.page.Page;

public class PageContext {

	public static final ThreadLocal<Page<Object>> localPage = new ThreadLocal<Page<Object>>();
	
	public static void setPage(Page<Object> page){
		localPage.set(page);
	}
	
	public static Page<Object> getPage(){
		return localPage.get();
	}
	
	public static void clear(){
		localPage.remove();
	}
}
