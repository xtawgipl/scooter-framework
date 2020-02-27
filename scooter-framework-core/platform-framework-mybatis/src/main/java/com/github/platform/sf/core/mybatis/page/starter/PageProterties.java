package com.github.platform.sf.core.mybatis.page.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="pagehelper")
public class PageProterties {
	
	/** 如果输入非法则默认第一页 */
	private int defaultPageNo;

	/** 如果如果输入非法则默认页大小*/
	private int defaultPageSize;
	
	private final Count count = new Count();

	public Count getCount() {
		return count;
	}

	public int getDefaultPageNo() {
		return defaultPageNo;
	}

	public void setDefaultPageNo(int defaultPageNo) {
		this.defaultPageNo = defaultPageNo;
	}

	public int getDefaultPageSize() {
		return defaultPageSize;
	}

	public void setDefaultPageSize(int defaultPageSize) {
		this.defaultPageSize = defaultPageSize;
	}
	
	/** 分页时需要返回总页数据及总行数*/
	@ConfigurationProperties(prefix="pagehelper.count")
	public class Count{
		
		private boolean isCount;
		
		private long expireAfterAccess;
		
		private long maximumSize;

		public boolean isCount() {
			return isCount;
		}

		public void setIsCount(boolean isCount) {
			this.isCount = isCount;
		}

		public long getExpireAfterAccess() {
			return expireAfterAccess;
		}

		public void setExpireAfterAccess(long expireAfterAccess) {
			this.expireAfterAccess = expireAfterAccess;
		}

		public long getMaximumSize() {
			return maximumSize;
		}

		public void setMaximumSize(long maximumSize) {
			this.maximumSize = maximumSize;
		}
	}
	
}
