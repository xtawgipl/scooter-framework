package com.github.platform.sf.web.container;

import com.alibaba.cloud.nacos.registry.NacosAutoServiceRegistration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author zhangjj
 * @create 2019-11-27 18:23
 **/

@Component
@Slf4j
public class NacosEventLinsiner implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired(required = false)
    private NacosAutoServiceRegistration registration;

    @Value("${server.port}")
    private Integer port;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("nacos 启动 --> registration = {}", registration);
        if (registration != null) {
            registration.setPort(port);
            registration.start();
        }
    }
}
