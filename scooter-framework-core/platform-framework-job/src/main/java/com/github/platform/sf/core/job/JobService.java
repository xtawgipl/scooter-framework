package com.github.platform.sf.core.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * 操作任务接口
 *
 * @author 张剑军
 * @Description
 * @date 2019/6/15 14:13
 **/
@Service
@Slf4j
public class JobService {

    private static final byte[] LOCK = new byte[8];

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;


    /**
     * 批量启动任务
     * @param jobModels
     */
    public void batchStart(Class<? extends AbstractJobActuator> jobActuator, List<JobModel> jobModels){
        if(jobModels == null || jobModels.isEmpty()){
            return ;
        }
        for(JobModel jobModel : jobModels){
            start(jobActuator, jobModel);
        }
    }

    /**
     * 批量停止任务
     * @param jobModels
     */
    public void batchStop(List<JobModel> jobModels){
        if(jobModels == null || jobModels.isEmpty()){
            return ;
        }
        for(JobModel jobModel : jobModels){
            stop(jobModel);
        }
    }

    /**
     * 批量重启任务
     * @param jobModels
     */
    public void batchRestart(Class<? extends AbstractJobActuator> jobActuator, List<JobModel> jobModels){
        if(jobModels == null || jobModels.isEmpty()){
            return ;
        }
        for(JobModel jobModel : jobModels){
            restart(jobActuator, jobModel);
        }
    }


    /**
     * 启动单个任务
     * @param jobModel
     * @return
     */
    public boolean start(Class<? extends AbstractJobActuator> jobActuator, JobModel jobModel){
        if (jobModel == null) return false;
        synchronized (LOCK) {
            try {
                JobKey jobKey = JobUtil.getJobKey(jobModel);
                Scheduler scheduler = schedulerFactoryBean.getScheduler();
                boolean exist = scheduler.checkExists(jobKey);
                if(exist){ // 存在则不处理
                    log.info("任务已经存在忽略start指令, jobModel = {}", jobModel);
                }else{
                    JobDataMap map = JobUtil.getJobDataMap(jobModel);
                    JobDetail jobDetail = JobUtil.geJobDetail(jobActuator, jobKey, jobModel.getDescription(), map);
                    scheduler.scheduleJob(jobDetail, JobUtil.getTrigger(jobModel));
                    log.info("任务启动成功 {}", jobModel);
                }
                return true;
            } catch (SchedulerException e) {
                log.error(String.format("任务启动失败 jobModel = %s", jobModel), e);
                return false;
            }
        }
    }

    /**
     * 停止单个任务
     * @param jobModel
     * @return
     */
    public boolean stop(JobModel jobModel){
        if (jobModel == null) return false;
        synchronized (LOCK) {
            try {
                JobKey jobKey = JobUtil.getJobKey(jobModel);
                Scheduler scheduler = schedulerFactoryBean.getScheduler();
                boolean exist = scheduler.checkExists(jobKey);
                if(!exist){// 不存在则不处理
                    log.info("任务不存在忽略stop指令, jobModel = {}", jobModel);
                }else{
                    scheduler.pauseJob(jobKey);
                    scheduler.unscheduleJob(TriggerKey.triggerKey(jobKey.getName(), jobKey.getGroup()));
                    scheduler.deleteJob(jobKey);
                    log.info("任务停止成功 {}", jobModel);
                }
                return true;
            } catch (SchedulerException e) {
                log.error(String.format("任务停止失败 jobModel = %s", jobModel), e);
                return false;
            }
        }
    }


    /**
     * 重启单个任务
     * @param jobModel
     * @return
     */
    public boolean restart(Class<? extends AbstractJobActuator> jobActuator, JobModel jobModel){
        if (jobModel == null) return false;
        synchronized (LOCK) {
            JobKey jobKey = JobUtil.getJobKey(jobModel);
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            try {
                scheduler.pauseJob(jobKey);
                log.info("任务暂停成功 group = {}, name = {}", jobModel.getGroup(), jobModel.getName());
                scheduler.unscheduleJob(TriggerKey.triggerKey(jobKey.getName(), jobKey.getGroup()));
                log.info("任务取消成功 group = {}, name = {}", jobModel.getGroup(), jobModel.getName());
                scheduler.deleteJob(jobKey);
                log.info("任务删除成功 group = {}, name = {}", jobModel.getGroup(), jobModel.getName());
                JobDataMap map = JobUtil.getJobDataMap(jobModel);
                JobDetail jobDetail = JobUtil.geJobDetail(jobActuator, jobKey, jobModel.getDescription(), map);
                scheduler.scheduleJob(jobDetail, JobUtil.getTrigger(jobModel));
                log.info("任务启动成功 group = {}, name = {}", jobModel.getGroup(), jobModel.getName());
                log.info("任务重启成功 {}", jobModel);
                return true;
            } catch (SchedulerException e) {
                log.error(String.format("任务重启失败 jobModel = %s", jobModel), e);
                return false;
            }
        }
    }



}
