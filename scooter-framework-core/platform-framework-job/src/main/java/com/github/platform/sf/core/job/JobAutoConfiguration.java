package com.github.platform.sf.core.job;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author zhangjj
 * @create 2020-03-17 14:19
 **/
@Configuration
@Import(JobService.class)
@ConditionalOnMissingBean(JobAutoConfiguration.class)
public class JobAutoConfiguration {

}
