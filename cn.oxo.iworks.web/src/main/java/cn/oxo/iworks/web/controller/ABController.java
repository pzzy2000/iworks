package cn.oxo.iworks.web.controller;
//package cn.zy.dev.tools.web.spring.shiro.tnmp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class ABController  {
	
	@Autowired
	protected HttpServletRequest request;

	@Autowired
	protected HttpServletResponse response;

	
}
