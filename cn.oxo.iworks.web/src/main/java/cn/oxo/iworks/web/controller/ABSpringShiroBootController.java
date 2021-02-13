package cn.oxo.iworks.web.controller;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import cn.oxo.iworks.web.LoginSessionBean;

public class ABSpringShiroBootController extends ABController {

	protected String getRequestParams() {
		return this.request.getRequestURI();
	}

	@Autowired
	protected Environment env;

	public void putSession(LoginSessionBean iSysManagerUser) {
		ShiroSubjectFactory.saveUserInfo(iSysManagerUser);

	}
	
	public void put(String key ,Serializable  object) {
		ShiroSubjectFactory.saveSessions(key, object);

	}

	public LoginSessionBean getLoginUser() {
		return ShiroSubjectFactory.getUserInfo();

	}

	public Long getLoginUserId() {

		LoginSessionBean loginBean = getLoginUser();

		return loginBean == null ? null : loginBean.getId();
	}

}
