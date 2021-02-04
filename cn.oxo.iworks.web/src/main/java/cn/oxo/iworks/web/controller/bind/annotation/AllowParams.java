package cn.oxo.iworks.web.controller.bind.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AllowParams {
	
	public enum  Type{
		exclude,include,none
	}

	//返回个格式 bean:field,field;bean:field,field;
	
	/**
	 * 1: 包含 ;0:排除
	 * @return
	 */
	public Type    type();
	/**
	 * 格式 bean:field,field;bean:field,field;
	 * @return
	 */
	public String[] field() ;

}
