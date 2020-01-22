package com.github.platform.sf.common.spring.scheduler;

import org.quartz.*;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * @author 张剑军
 * @Description
 * @date 2019/6/15 14:21
 **/
public class JobUtil {

    /**
     * 获取JobDataMap.(Job参数对象)
     * @param model
     * @return
     */
    public static JobDataMap getJobDataMap(JobModel model) {
        JobDataMap map = new JobDataMap();
        if(model == null){
            return map;
        }
        Field[] fields = model.getClass().getDeclaredFields();
        for(Field field : fields){
            ReflectionUtils.makeAccessible(field);
            map.put(field.getName(), ReflectionUtils.getField(field, model));
        }
        return map;
    }

    /**
     * 获取JobDetail,JobDetail是任务的定义,而Job是任务的执行逻辑,
     * JobDetail里会引用一个Job Class来定义
     * @param jobKey
     * @param description
     * @param map
     * @return
     */
    public static JobDetail geJobDetail(Class<? extends Job> job, JobKey jobKey, String description, JobDataMap map) {
        return JobBuilder.newJob(job)
                .withIdentity(jobKey)
                .withDescription(description)
                .setJobData(map)
                .storeDurably()
                .build();
    }

    /**
     * 获取Trigger (Job的触发器,执行规则)
     * @param job
     * @return
     */
    public static Trigger getTrigger(JobModel job) {
        return TriggerBuilder.newTrigger()
                .withIdentity(job.getName(), job.getGroup())
                .withSchedule(CronScheduleBuilder.cronSchedule(job.getCronExpression()))
                .build();
    }


    /**
     * 获取JobKey,包含Name和Group
     * @param job
     * @return
     */
    public static JobKey getJobKey(JobModel job) {
        return JobKey.jobKey(job.getName(), job.getGroup());
    }

}
