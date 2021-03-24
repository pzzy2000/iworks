package cn.oxo.iworks.task.quartz.service;

import java.io.Serializable;
import java.util.Date;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
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

import com.alibaba.fastjson.JSONObject;

import cn.oxo.iworks.task.quartz.CronDateUnits;
import cn.oxo.iworks.task.quartz.ExecQuartzTask;
import cn.oxo.iworks.task.quartz.SchedulerQuartzException;

// https://blog.csdn.net/pan_junbiao/article/details/109328069

//https://blog.csdn.net/zixiao217/article/details/53091812
//https://blog.csdn.net/weixin_34062469/article/details/85562155
//@Component
public abstract class QuartzService implements IQuartzService {

	@Autowired
	private Scheduler scheduler;

	@Override
	public <V extends ExecQuartzTask> void submitTask(String taskGroup, Long taskId,  Serializable params, Date excTime, Class<V> quartzTaskClass)
			throws SchedulerQuartzException {
		try {

			JobDetail job = JobBuilder.newJob(quartzTaskClass).withIdentity(taskId.toString(), taskGroup).requestRecovery().storeDurably(true).build();
			
			job.getJobDataMap().put(IQuartzService.key_task_params_json, JSONObject.toJSON(params).toString());

			// Trigger the job to run now, and then repeat every 40 seconds
			Trigger trigger = TriggerBuilder.newTrigger().withIdentity(taskId.toString(), taskGroup).startNow()
					.withSchedule(CronScheduleBuilder.cronSchedule(CronDateUnits.getCron(excTime))).build();
			// Tell quartz to schedule the job using our trigger
			scheduler.scheduleJob(job, trigger);
		} catch (SchedulerException e) {
			throw new SchedulerQuartzException(e);
		}

	}

	@Override
	public <V extends ExecQuartzTask> void submitTask(String taskGroup, Long taskId, Serializable params, String cronTime, Class<V> quartzTaskClass)
			throws SchedulerQuartzException {
		try {

			JobDetail job = JobBuilder.newJob(quartzTaskClass).withIdentity(taskId.toString(), taskGroup).storeDurably(true) .build();
			job.getJobDataMap().put(IQuartzService.key_task_params_json,JSONObject.toJSON(params).toString());
			// Trigger the job to run now, and then repeat every 40 seconds

			Trigger trigger = TriggerBuilder.newTrigger().withIdentity(taskId.toString(), taskGroup).startNow()
					.withSchedule(CronScheduleBuilder.cronSchedule(cronTime)).build();
			// Tell quartz to schedule the job using our trigger
			scheduler.scheduleJob(job, trigger);

		} catch (SchedulerException e) {
			throw new SchedulerQuartzException(e);
		}

	}

	@Override
	public <V extends ExecQuartzTask> void submitTaskBy(String taskGroup, Long taskId, Class<V> quartzTaskClass, Serializable params, Date startTime,
			int secondsSpace) throws SchedulerQuartzException {
		try {
			JobDetail job = JobBuilder.newJob(quartzTaskClass).withIdentity(taskId.toString(), taskGroup).storeDurably(true) .build();
			job.getJobDataMap().put(IQuartzService.key_task_params_json,JSONObject.toJSON(params).toString());
            System.out.println(">>  "+job.isDurable());
			SimpleTrigger simpleTrigger = (SimpleTrigger) TriggerBuilder.newTrigger().withIdentity(taskId.toString(), taskGroup).startAt(startTime)
					.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(secondsSpace).repeatForever()).build();

			scheduler.scheduleJob(job, simpleTrigger);
		} catch (SchedulerException e) {
			throw new SchedulerQuartzException(e);
		}

	}

	@Override
	public void cancelTask(String taskGroup, Long taskId) throws SchedulerQuartzException {
		try {
			JobKey jobKey = new JobKey(taskId.toString(), taskGroup);
			JobDetail jobDetail = scheduler.getJobDetail(jobKey);
			if (jobDetail == null)
				return;
			TriggerKey triggerKey = TriggerKey.triggerKey(taskId.toString(), taskGroup);
			scheduler.pauseTrigger(triggerKey);// 停止触发器
			scheduler.unscheduleJob(triggerKey);// 删除任务
			scheduler.deleteJob(jobKey);
		} catch (SchedulerException e) {
			throw new SchedulerQuartzException(e);
		}

	}

	@Override
	public <V extends ExecQuartzTask>  void updateTaskParams(String taskGroup, Long taskId,Class<V> quartzTaskClass, Serializable params) throws SchedulerQuartzException {

		try {
			JobKey jobKey = new JobKey(taskId.toString(), taskGroup);

			if (!scheduler.checkExists(jobKey))
				throw new SchedulerQuartzException("not find job Key taskGroup " + taskGroup + " taskId " + taskId);

			JobDetail jobDetail =  JobBuilder.newJob(quartzTaskClass).withIdentity(taskId.toString(), taskGroup).build(); 
			jobDetail.getJobDataMap().put(IQuartzService.key_task_params_json, JSONObject.toJSON(params).toString());
			scheduler.addJob(jobDetail, true);
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

}
