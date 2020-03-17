package com.github.platform.sf.core.redis;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author zhangjj
 * @create 2020-03-10 17:51
 **/
@Configuration
@Import({RedisConfig.class, RedisOperator.class})
@ConditionalOnMissingBean(RedisAutoConfiguration.class)
public class RedisAutoConfiguration {

}
