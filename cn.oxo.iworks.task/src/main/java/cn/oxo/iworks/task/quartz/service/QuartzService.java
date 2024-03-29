package cn.oxo.iworks.task.quartz.service;

import java.io.Serializable;
import java.util.Date;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;

import cn.oxo.iworks.task.quartz.CronDateUnits;
import cn.oxo.iworks.task.quartz.ExecQuartzTask;
import cn.oxo.iworks.task.quartz.SchedulerQuartzException;

// https://blog.csdn.net/pan_junbiao/article/details/109328069

// https://blog.csdn.net/zixiao217/article/details/53091812
// https://blog.csdn.net/weixin_34062469/article/details/85562155
// @Component
public abstract class QuartzService implements IQuartzService {

      @Autowired
      private Scheduler scheduler;

      @Override
      public <V extends ExecQuartzTask> void submitTask(String taskGroup, String taskId, Serializable params, Date excTime, Class<V> QuartzTaskClass) throws SchedulerQuartzException {
            submitTask(taskGroup, taskId, params, excTime, QuartzTaskClass, false);

      }

      @Override
      public <V extends ExecQuartzTask> void submitIntervalTask(String taskGroup, String taskId, Class<V> quartzTaskClass, Serializable params, Date startTime, int secondsSpace)
                                          throws SchedulerQuartzException {
            submitIntervalTask(taskGroup, taskId, quartzTaskClass, params, startTime, secondsSpace, false);

      }

      @Override
      public <V extends ExecQuartzTask> void submitTask(String taskGroup, String taskId, Serializable params, String cronTime, Class<V> QuartzTaskClass) throws SchedulerQuartzException {
            submitTask(taskGroup, taskId, params, cronTime, QuartzTaskClass, false);

      }

      private void overwriteTask(String taskGroup, String taskId, boolean overwrite) throws SchedulerQuartzException {
            JobKey jobKey = new JobKey(taskId.toString(), taskGroup);
            boolean exist = existTask(jobKey);
            if (exist == true) {
                  if (overwrite == true) {
                        cancelTask(jobKey);
                  } else {
                        throw new SchedulerQuartzException("task taskGroup :  " + taskGroup + " taskId : " + taskId + " exist");
                  }
            }
      }

      @Override
      public <V extends ExecQuartzTask> void submitTask(String taskGroup, String taskId, Serializable params, Date excTime, Class<V> quartzTaskClass, boolean overwrite)
                                          throws SchedulerQuartzException {
            try {

                  overwriteTask(taskGroup, taskId, overwrite);

                  JobDetail job = JobBuilder.newJob(quartzTaskClass).withIdentity(taskId.toString(), taskGroup).requestRecovery().storeDurably(true).build();

                  job.getJobDataMap().put(IQuartzService.key_task_params_json, JSON.toJSONString(params));

                  // Trigger the job to run now, and then repeat every 40
                  // seconds
                  Trigger trigger = TriggerBuilder.newTrigger().withIdentity(taskId.toString(), taskGroup).startNow().withSchedule(CronScheduleBuilder.cronSchedule(CronDateUnits.getCron(excTime)))
                                                      .build();
                  // Tell quartz to schedule the job using our trigger
                  scheduler.scheduleJob(job, trigger);
            } catch (SchedulerException e) {
                  throw new SchedulerQuartzException(e);
            }

      }

      @Override
      public <V extends ExecQuartzTask> void submitTask(String taskGroup, String taskId, Serializable params, String cronTime, Class<V> quartzTaskClass, boolean overwrite)
                                          throws SchedulerQuartzException {
            try {
                  overwriteTask(taskGroup, taskId, overwrite);
                  JobDetail job = JobBuilder.newJob(quartzTaskClass).withIdentity(taskId.toString(), taskGroup).storeDurably(true).build();
                  job.getJobDataMap().put(IQuartzService.key_task_params_json, JSON.toJSONString(params));
                  // Trigger the job to run now, and then repeat every 40
                  // seconds

                  Trigger trigger = TriggerBuilder.newTrigger().withIdentity(taskId.toString(), taskGroup).startNow().withSchedule(CronScheduleBuilder.cronSchedule(cronTime)).build();
                  // Tell quartz to schedule the job using our trigger
                  scheduler.scheduleJob(job, trigger);

            } catch (SchedulerException e) {
                  throw new SchedulerQuartzException(e);
            }

      }

      @Override
      public <V extends ExecQuartzTask> void submitIntervalTask(String taskGroup, String taskId, Class<V> quartzTaskClass, Serializable params, Date startTime, int secondsSpace, boolean overwrite)
                                          throws SchedulerQuartzException {
            try {
                  overwriteTask(taskGroup, taskId, overwrite);
                  JobDetail job = JobBuilder.newJob(quartzTaskClass).withIdentity(taskId.toString(), taskGroup).storeDurably(true).build();
                  job.getJobDataMap().put(IQuartzService.key_task_params_json, JSON.toJSONString(params));
                  SimpleTrigger simpleTrigger = TriggerBuilder.newTrigger().withIdentity(taskId.toString(), taskGroup).startAt(startTime)
                                                      .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(secondsSpace).repeatForever()).build();

                  scheduler.scheduleJob(job, simpleTrigger);

            } catch (SchedulerException e) {
                  throw new SchedulerQuartzException(e);
            }

      }

      @Override
      public void cancelTask(String taskGroup, String taskId) throws SchedulerQuartzException {

            JobKey jobKey = new JobKey(taskId.toString(), taskGroup);
            cancelTask(jobKey);

      }

      private void cancelTask(JobKey jobKey) throws SchedulerQuartzException {
            try {

                  JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                  if (jobDetail == null)
                        return;
                  TriggerKey triggerKey = TriggerKey.triggerKey(jobKey.getName(), jobKey.getGroup());
                  scheduler.pauseTrigger(triggerKey);// 停止触发器
                  scheduler.unscheduleJob(triggerKey);// 删除任务
                  scheduler.deleteJob(jobKey);
            } catch (SchedulerException e) {
                  throw new SchedulerQuartzException(e);
            }

      }

      @Override
      public <V extends ExecQuartzTask> void updateTaskParams(String taskGroup, String taskId, Class<V> quartzTaskClass, Serializable params) throws SchedulerQuartzException {

            try {
                  JobKey jobKey = new JobKey(taskId.toString(), taskGroup);

                  if (!scheduler.checkExists(jobKey))
                        throw new SchedulerQuartzException("not find job Key taskGroup " + taskGroup + " taskId " + taskId);

                  JobDetail jobDetail = JobBuilder.newJob(quartzTaskClass).withIdentity(taskId.toString(), taskGroup).storeDurably(true).build();
                  jobDetail.getJobDataMap().put(IQuartzService.key_task_params_json, JSON.toJSONString(params));
                  scheduler.addJob(jobDetail, true);

            } catch (SchedulerException e) {
                  throw new SchedulerQuartzException(e);
            }

      }

      @SuppressWarnings({ "rawtypes", "unchecked" })
      @Override
      public <V extends ExecQuartzTask> void updateTaskParams(String taskGroup, String taskId, Class<V> quartzTaskClass, Serializable params, int secondsSpace) throws SchedulerQuartzException {
            updateTaskParams(taskGroup, taskId, quartzTaskClass, params);
            // retrieve the trigger
            try {
                  Trigger oldTrigger = scheduler.getTrigger(TriggerKey.triggerKey(taskId.toString(), taskGroup));

                  TriggerBuilder tb = oldTrigger.getTriggerBuilder();

                  Trigger newTrigger = tb.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(secondsSpace).repeatForever()).build();

                  scheduler.rescheduleJob(oldTrigger.getKey(), newTrigger);

            } catch (SchedulerException e) {
                  throw new SchedulerQuartzException(e);
            }

      }

      @Override
      public void startAllTasks() throws SchedulerQuartzException {
            try {

                  scheduler.start();
            } catch (Exception e) {
                  throw new SchedulerQuartzException(e);
            }

      }

      @Override
      public void shutdownAllTasks() throws SchedulerQuartzException {
            try {

                  if (!scheduler.isShutdown()) {
                        scheduler.shutdown();
                  }
            } catch (Exception e) {
                  throw new SchedulerQuartzException(e);
            }
      }

      @Override
      public boolean existTask(String taskGroup, String taskId) throws SchedulerQuartzException {
            JobKey jobKey = new JobKey(taskId.toString(), taskGroup);

            return existTask(jobKey);
      }

      private boolean existTask(JobKey jobKey) throws SchedulerQuartzException {
            try {
                  boolean exist = scheduler.checkExists(jobKey);
                  return exist;
            } catch (SchedulerException e) {
                  throw new SchedulerQuartzException(e);
            }
      }

}
