package cn.oxo.iworks.task.quartz;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CronDateUnits {
	
	/**
	 * 通过输入指定日期时间生成cron表达式
	 * 
	 * @param date
	 * @return cron表达式
	 */
	public static  String getCron(Date date) {
		String dateFormat = "ss mm HH dd MM ? yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		String formatTimeStr = null;
		if (date != null) {
			formatTimeStr = sdf.format(date);
		}

		return formatTimeStr;
	}
	
	public static void main(String[] args) {
		System.out.println(">>  "+CronDateUnits.getCron(new Date()));
	}

}
