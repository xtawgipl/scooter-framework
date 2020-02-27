package com.github.platform.sf.core.mybatis.page.starter;

import com.github.platform.sf.core.mybatis.page.dialect.DialectType;
import com.github.platform.sf.core.mybatis.page.interceptor.PageInterceptor;
import com.github.platform.sf.core.mybatis.page.interceptor.PageProcessor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author zhangjj
 * @create 2020-02-20 11:49
 **/
@Configuration
@Import({PageProcessor.class})
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
@AutoConfigureBefore(MybatisAutoConfiguration.class)
public class PageAutoConfiguration {

    @Autowired
    private PageProterties pageProterties;

    @Autowired
    private DataSource dataSource;


    @Value("${mybatis.mapper-locations:classpath:mapper/*Mapper.xml}")
    private String mapperLocation;


    @Bean(name = "sqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("dialect", DialectType.MYSQL.getDialectName());
        properties.setProperty("count.isCount", String.valueOf(pageProterties.getCount().isCount()));
        properties.setProperty("count.expireAfterAccess", String.valueOf(pageProterties.getCount().getExpireAfterAccess()));
        properties.setProperty("count.maximumSize", String.valueOf(pageProterties.getCount().getMaximumSize()));
        pageInterceptor.setProperties(properties);
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPlugins(new Interceptor[] { pageInterceptor });
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocation));
        return sessionFactory.getObject();
    }
}
