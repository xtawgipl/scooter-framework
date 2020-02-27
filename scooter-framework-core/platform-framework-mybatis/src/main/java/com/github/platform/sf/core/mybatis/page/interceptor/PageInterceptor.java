package com.github.platform.sf.core.mybatis.page.interceptor;

import com.github.platform.sf.common.util.cache.GuavaCache;
import com.github.platform.sf.core.mybatis.page.Page;
import com.github.platform.sf.core.mybatis.page.dialect.AbstractDialect;
import com.github.platform.sf.core.mybatis.page.exception.PageException;
import com.github.platform.sf.core.mybatis.page.util.MSUtils;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Intercepts({
		@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
				RowBounds.class, ResultHandler.class }),
		@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
				RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class }), })
public class PageInterceptor implements Interceptor {
	private Logger logger = LoggerFactory.getLogger(PageInterceptor.class);
	
	private AbstractDialect dialect = null;
	
	private GuavaCache<CacheKey, MappedStatement> msCountMap = null;
	
	private boolean isCount = false;

	@SuppressWarnings({ "unchecked" })
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		Page<Object> page = PageContext.getPage();
		Object[] args = invocation.getArgs();
		MappedStatement ms = (MappedStatement) args[0];
		Object parameter = args[1];
		RowBounds rowBounds = (RowBounds) args[2];
		ResultHandler<?> resultHandler = (ResultHandler<?>) args[3];
		Executor executor = (Executor) invocation.getTarget();
		CacheKey cacheKey;
		BoundSql boundSql;
		// 由于逻辑关系，只会进入一次
		if (args.length == 4) {
			// 4 个参数时
			boundSql = ms.getBoundSql(parameter);
			cacheKey = executor.createCacheKey(ms, parameter, rowBounds, boundSql);
		} else {
			// 6 个参数时
			cacheKey = (CacheKey) args[4];
			boundSql = (BoundSql) args[5];
		}
		List<Object> resultList;
		// 调用方法判断是否需要进行分页，如果不需要，直接返回结果
		if (page != null) {
			// 反射获取动态参数
			Field additionalParametersField = BoundSql.class.getDeclaredField("additionalParameters");
			additionalParametersField.setAccessible(true);
			Map<String, Object> additionalParameters = (Map<String, Object>) additionalParametersField.get(boundSql);
			if(isCount){
				 //创建 count 查询的缓存 key
                CacheKey countKey = executor.createCacheKey(ms, parameter, RowBounds.DEFAULT, boundSql);
                countKey.update(MSUtils.COUNT);
                MappedStatement countMs = msCountMap.get(countKey);
                if (countMs == null) {
                    //根据当前的 ms 创建一个返回值为 Long 类型的 ms
                    countMs = MSUtils.newCountMappedStatement(ms);
                    msCountMap.put(countKey, countMs);
                }
                //调用方言获取 count sql
                String countSql = dialect.getCountSql(ms, boundSql, parameter, rowBounds, countKey);
                countKey.update(countSql);
                BoundSql countBoundSql = new BoundSql(ms.getConfiguration(), countSql, boundSql.getParameterMappings(), parameter);
                //当使用动态 SQL 时，可能会产生临时的参数，这些参数需要手动设置到新的 BoundSql 中
                for (String key : additionalParameters.keySet()) {
                    countBoundSql.setAdditionalParameter(key, additionalParameters.get(key));
                }
                //执行 count 查询
                Object countResultList = executor.query(countMs, parameter, RowBounds.DEFAULT, resultHandler, countKey, countBoundSql);
                Long count = (Long) ((List<Object>) countResultList).get(0);
                //处理查询总数
                //总数为0再直接返回空
                if (count <= 0) {
                    //当查询总数为 0 时，直接返回空的结果
                    return new ArrayList<Object>();
                }else{
                	page.setTotalSize(count.intValue());
                	page.setTotalPage((count.intValue() + page.getPageSize() - 1) / page.getPageSize());
                }
			}
			
			// 判断是否需要进行分页查询
			// 生成分页的缓存 key
			CacheKey pageKey = cacheKey;
			// 处理参数对象
			// 调用方言获取分页 sql
			String pageSql = dialect.getPageSql(boundSql.getSql(), page);
			BoundSql pageBoundSql = new BoundSql(ms.getConfiguration(), pageSql, boundSql.getParameterMappings(), parameter);
			// 设置动态参数
			for (String key : additionalParameters.keySet()) {
				pageBoundSql.setAdditionalParameter(key, additionalParameters.get(key));
			}
			// 执行分页查询
			resultList = executor.query(ms, parameter, RowBounds.DEFAULT, resultHandler, pageKey, pageBoundSql);
			page.setResults(resultList);
		} else {
			// rowBounds用参数值，不使用分页插件处理时，仍然支持默认的内存分页
			resultList =  executor.query(ms, parameter, rowBounds, resultHandler, cacheKey, boundSql);
		}
		return resultList;
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		String dialectClass = properties.getProperty("dialect");
		Class<?> aClass = null;
		try {
			aClass = Class.forName(dialectClass);
			dialect = (AbstractDialect) aClass.newInstance();
		} catch (Exception e) {
			logger.error("", e);
			throw new PageException("不存在的数据库方言dialect,请检查pagehelper.helperDialect 配置值!", e);
		}
		
		isCount = Boolean.valueOf(properties.getProperty("count.isCount"));
		long expireAfterAccess = Long.valueOf(properties.getProperty("count.expireAfterAccess"));
    	long maximumSize = Long.valueOf(properties.getProperty("count.maximumSize"));
		msCountMap = new GuavaCache<>(expireAfterAccess, maximumSize);
	}
}
