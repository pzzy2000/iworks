package cn.oxo.iworks.task.quartz.service;

import java.io.Serializable;
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
	public <V extends ExecQuartzTask> void submitTask(String taskGroup, Long taskId, Serializable params, Date excTime, Class<V> QuartzTaskClass,boolean overwrite)
			throws SchedulerQuartzException;
	
	public <V extends ExecQuartzTask> void submitTask(String taskGroup, Long taskId, Serializable params, Date excTime, Class<V> QuartzTaskClass)throws SchedulerQuartzException;

	public <V extends ExecQuartzTask> void submitTask(String taskGroup, Long taskId, Serializable params, String cronTime, Class<V> QuartzTaskClass,boolean overwrite)throws SchedulerQuartzException;

	public <V extends ExecQuartzTask> void submitTask(String taskGroup, Long taskId, Serializable params, String cronTime, Class<V> QuartzTaskClass)throws SchedulerQuartzException;

	
	
	public <V extends ExecQuartzTask>  void updateTaskParams(String taskGroup, Long taskId,Class<V> quartzTaskClass ,Serializable params) throws SchedulerQuartzException;
	
	public <V extends ExecQuartzTask>  void updateTaskParams(String taskGroup, Long taskId,Class<V> quartzTaskClass ,Serializable params,int secondsSpace) throws SchedulerQuartzException;

	public <V extends ExecQuartzTask> void submitIntervalTask(String taskGroup, Long taskId, Class<V> quartzTaskClass, Serializable params, Date startTime,
			int secondsSpace) throws SchedulerQuartzException;
	
	public <V extends ExecQuartzTask> void submitIntervalTask(String taskGroup, Long taskId, Class<V> quartzTaskClass, Serializable params, Date startTime,
			int secondsSpace,boolean overwrite) throws SchedulerQuartzException;

	public void cancelTask(String taskGroup, Long taskId) throws SchedulerQuartzException;
	
	public boolean existTask(String taskGroup, Long taskId) throws SchedulerQuartzException;

	/**
	 * 启动所有定时任务
	 * 
	 * @throws SchedulerQuartzException
	 */
	public void startAllTasks() throws SchedulerQuartzException;

	public void shutdownAllTasks() throws SchedulerQuartzException;
}
