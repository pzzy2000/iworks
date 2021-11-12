package cn.oxo.iworks.tools;

import org.apache.commons.lang3.StringUtils;

import cn.oxo.iworks.databases.SysOptError;
import cn.oxo.iworks.databases.SystemOptServiceException;

public class CheckUnits {

	/**
	 * throw new SystemOptServiceException(SysOptError.ParamsError.getCode(), msg);
	 * @param value
	 * @param msg
	 */
	public static void isEmpty(String value, String msg) {

		if (StringUtils.isEmpty(value))
			throw new SystemOptServiceException(SysOptError.ParamsError.getCode(), msg);
	}

	public static boolean  isEmpty(String value) {
        
		   return StringUtils.isEmpty(value);
		 
	}
	
	public static void isEmpty(Object value, String msg) {

		if (value ==null)
			throw new SystemOptServiceException(SysOptError.ParamsError.getCode(), msg);
	}
}
