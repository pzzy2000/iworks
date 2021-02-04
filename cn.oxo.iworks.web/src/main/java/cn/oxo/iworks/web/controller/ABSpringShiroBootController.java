package cn.oxo.iworks.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public class ABSpringShiroBootController extends ABController {

	protected String getRequestParams() {
		return this.request.getRequestURI();
	}

	@Autowired
	protected Environment env;

	public <V> V getLoginUserInfo() {

		return ShiroSubjectFactory.getUserInfo();

	}

	public <V> V searchSession(String key) {
		return ShiroSubjectFactory.getSession(key);

	}

}
