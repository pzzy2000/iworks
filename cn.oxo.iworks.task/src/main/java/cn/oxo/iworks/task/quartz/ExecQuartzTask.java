package cn.oxo.iworks.task.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.alibaba.fastjson.JSONObject;

import cn.oxo.iworks.task.quartz.service.IQuartzService;

public abstract class ExecQuartzTask extends QuartzJobBean {

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		JobKey key = context.getJobDetail().getKey();

		String params = (String) context.getJobDetail().getJobDataMap().get(IQuartzService.key_task_params_json);
         
		exec(key, JSONObject.parseObject(params));

	}

	protected abstract void exec(JobKey key, JSONObject  params);
}
