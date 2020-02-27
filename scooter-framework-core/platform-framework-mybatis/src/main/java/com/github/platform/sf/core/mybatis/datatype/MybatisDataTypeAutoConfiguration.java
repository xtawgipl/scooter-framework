package com.github.platform.sf.core.mybatis.datatype;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author zhangjj
 * @create 2020-02-20 14:50
 **/
@Configuration
@ConditionalOnMissingBean(MybatisDataTypeAutoConfiguration.class)
public class MybatisDataTypeAutoConfiguration  {

    @Value("${mybatis-config.dataSecurity.key:defKey}")
    private String dataSecurityKey;

    @PostConstruct
    public void init(){
        System.setProperty("cipherKey", dataSecurityKey);
    }
}
