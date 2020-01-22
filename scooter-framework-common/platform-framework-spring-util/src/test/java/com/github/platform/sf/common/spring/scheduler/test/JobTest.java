package com.github.platform.sf.common.spring.scheduler.test;

import com.github.platform.sf.common.spring.scheduler.JobModel;
import com.github.platform.sf.common.spring.scheduler.JobService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

/**
 * @author zhangjj
 * @create 2020-01-22 11:36
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = JobTest.class)
@SpringBootApplication
@ComponentScan("com.github.platform.sf.common.spring.scheduler")
public class JobTest {

    @Autowired
    private JobService jobService;


    @Test
    public void start() throws InterruptedException {
        JobModel jobModel = new JobModel();
        jobModel.setGroup("monitor-terminal");
        jobModel.setName("hearbeat");
        jobModel.setJobDescription("test");
        jobModel.setCronExpression("0/10 * * * * ?");
        jobModel.setExtData(new JobTest());
        jobService.start(SimpleJobActuator.class, jobModel);
        TimeUnit.SECONDS.sleep(600);
    }
}
