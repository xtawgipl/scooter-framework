package com.github.platform.sf.common.spring.scheduler.test;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Service;

/**
 * @author 张剑军
 * @Description
 * @date 2019/6/15 15:02
 **/
@Service
@Slf4j
public class SimpleJobActuator implements Job {


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        String[] keys = jobDataMap.getKeys();
        for(String key : keys){
            Object o = jobDataMap.get(key);
            log.info("{} --> {}", key, o);
        }
    }
}
