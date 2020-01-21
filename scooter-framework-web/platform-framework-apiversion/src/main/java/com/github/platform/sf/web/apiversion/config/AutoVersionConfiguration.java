package com.github.platform.sf.web.apiversion.config;

import com.github.platform.sf.web.apiversion.spring.WebMvcRegistrationsConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangjj
 * @create 2019-12-04 11:26
 **/
@Configuration
@ConditionalOnClass(AutoVersionConfiguration.class)
@Slf4j
public class AutoVersionConfiguration {

    @Bean
    public WebMvcRegistrationsConfig webMvcRegistrationsConfig(){
        log.info("加载接口版本控制器 : {}", WebMvcRegistrationsConfig.class.getName());
        return new WebMvcRegistrationsConfig();
    }
}
