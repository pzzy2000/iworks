package cn.oxo.iworks.task.quartz.service;

import java.util.Date;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;

import cn.oxo.iworks.task.quartz.CronDateUnits;
import cn.oxo.iworks.task.quartz.ExecQuartzTask;
import cn.oxo.iworks.task.quartz.SchedulerQuartzException;

// https://blog.csdn.net/pan_junbiao/article/details/109328069
//@Component
public abstract class QuartzService implements IQuartzService {

	@Autowired
	private Scheduler scheduler;

	@Override
	public <V extends ExecQuartzTask> void submitTask(String taskGroup, Long taskId, String params, Date excTime, Class<V> quartzTaskClass)
			throws SchedulerQuartzException {
		try {
			
			JobDetail job = JobBuilder.newJob(quartzTaskClass).withIdentity(taskId.toString(), taskGroup).usingJobData(IQuartzService.key_task_params_json, params).build();

			// Trigger the job to run now, and then repeat every 40 seconds
			Trigger trigger = TriggerBuilder.newTrigger().withIdentity(taskId.toString(), taskGroup).startNow().withSchedule(CronScheduleBuilder.cronSchedule(CronDateUnits.getCron(excTime))).build();
			// Tell quartz to schedule the job using our trigger
			scheduler.scheduleJob(job, trigger);
		} catch (SchedulerException e) {
			throw new SchedulerQuartzException(e);
		}

	}

	@Override
	public <V extends ExecQuartzTask> void submitTask(String taskGroup, Long taskId, String params, String cronTime, Class<V> quartzTaskClass)
			throws SchedulerQuartzException {
		try {
			JobDetail job = JobBuilder.newJob(quartzTaskClass).withIdentity(taskId.toString(), taskGroup).usingJobData(IQuartzService.key_task_params_json, params).build();

			// Trigger the job to run now, and then repeat every 40 seconds

			Trigger trigger = TriggerBuilder.newTrigger().withIdentity(taskId.toString(), taskGroup).startNow().withSchedule(CronScheduleBuilder.cronSchedule(cronTime)).build();
			// Tell quartz to schedule the job using our trigger
			scheduler.scheduleJob(job, trigger);

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
