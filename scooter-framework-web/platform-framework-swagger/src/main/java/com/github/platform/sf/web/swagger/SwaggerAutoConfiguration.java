package com.github.platform.sf.web.swagger;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author zhangjj
 * @create 2020-02-27 11:25
 **/
@Configuration
@Import(Swagger2Configuration.class)
@ConditionalOnProperty(value = "swagger.enabled", havingValue = "true")
public class SwaggerAutoConfiguration {

}
