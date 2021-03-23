package cn.oxo.iworks.task.quartz.service;

import java.util.Date;

import cn.oxo.iworks.task.quartz.ExecQuartzTask;
import cn.oxo.iworks.task.quartz.SchedulerQuartzException;

public interface IQuartzService {

	public String key_task_params_json = "params";

	/**
	 * 指定时间执行
	 * 
	 * @param taskGroup
	 * @param taskId
	 * @param params
	 * @param excTime
	 * @param QuartzTaskClass
	 * @throws SchedulerQuartzException
	 */
	public <V extends ExecQuartzTask> void submitTask(String taskGroup, Long taskId, String params, Date excTime, Class<V> QuartzTaskClass)
			throws SchedulerQuartzException;

	public <V extends ExecQuartzTask> void submitTask(String taskGroup, Long taskId, String params, String cronTime, Class<V> QuartzTaskClass)
			throws SchedulerQuartzException;

	public <V extends ExecQuartzTask> void submitTaskBy(String taskGroup, Long taskId, Class<V> quartzTaskClass, String params, Date startTime,
			int secondsSpace) throws SchedulerQuartzException;

	public void cancelTask(String taskGroup, Long taskId) throws SchedulerQuartzException;

	/**
	 * 启动所有定时任务
	 * 
	 * @throws SchedulerQuartzException
	 */
	public void startAllTasks() throws SchedulerQuartzException;

	public void shutdownAllTasks() throws SchedulerQuartzException;
}
