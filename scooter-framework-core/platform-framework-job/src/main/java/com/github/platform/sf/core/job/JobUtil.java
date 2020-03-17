package com.github.platform.sf.core.job;

import org.quartz.*;

/**
 * @author 张剑军
 * @Description
 * @date 2019/6/15 14:21
 **/
public class JobUtil {

    /**
     * 获取JobDataMap.(Job参数对象)
     * @param job
     * @return
     */
    public static JobDataMap getJobDataMap(JobModel job) {
        JobDataMap map = new JobDataMap();
        map.put("name", job.getName());
        map.put("group", job.getGroup());
        map.put("cronExpression", job.getCron());
        map.put("JobDescription", job.getDescription());
        map.put("data", job.getData());
        return map;
    }


    public static <T> JobModel<T> getJobModel(JobDataMap jobDataMap) {
        JobModel<T> jobModel = new JobModel<>();
        jobModel.setName(jobDataMap.getString("name"));
        jobModel.setGroup(jobDataMap.getString("group"));
        jobModel.setCron(jobDataMap.getString("cronExpression"));
        jobModel.setDescription(jobDataMap.getString("JobDescription"));
        Object data = jobDataMap.get("data");
        if(data != null){
            jobModel.setData((T) data);
        }
        return jobModel;
    }


    /**
     * 获取JobDetail,JobDetail是任务的定义,而Job是任务的执行逻辑,
     * JobDetail里会引用一个Job Class来定义
     * @param jobKey
     * @param description
     * @param map
     * @return
     */
    public static JobDetail geJobDetail(Class<? extends AbstractJobActuator> jobActuator, JobKey jobKey, String description, JobDataMap map) {
        return JobBuilder.newJob(jobActuator)
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
                .withSchedule(CronScheduleBuilder.cronSchedule(job.getCron()))
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
