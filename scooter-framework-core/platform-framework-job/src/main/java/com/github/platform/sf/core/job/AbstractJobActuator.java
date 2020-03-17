package com.github.platform.sf.core.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


/**
 * 执行器
 * @author 张剑军
 * @Description
 * @date 2019/6/15 14:27
 **/
@Slf4j
public abstract class AbstractJobActuator implements Job {

    protected abstract void doExecute(JobModel jobModel);

    /**
     * 核心方法,Quartz Job真正的执行逻辑.
     * @param executorContext executorContext JobExecutionContext中封装有Quartz运行所需要的所有信息
     * @throws JobExecutionException execute()方法只允许抛出JobExecutionException异常
     */
    @Override
    public void execute(JobExecutionContext executorContext) throws JobExecutionException {
        //JobDetail中的JobDataMap是共用的,从getMergedJobDataMap获取的JobDataMap是全新的对象
        JobDataMap map = executorContext.getMergedJobDataMap();

        JobModel jobModel = JobUtil.getJobModel(map);

        log.info("Running Job : \n{} ", jobModel);

        doExecute(jobModel);
    }


}
