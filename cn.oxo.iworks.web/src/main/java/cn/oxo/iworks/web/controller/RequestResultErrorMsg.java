package cn.oxo.iworks.web.controller;

public enum RequestResultErrorMsg {

	sys_unknown_error(1000, "未知错误"),

	sys_forward_error(1001, "系统错误[forward]"),

	sys_encoding_error(1002, "系统错误[encoding]"),

	sys_no_have_code_error(1003, "系统错误[参数错误]"),

	sys_authorize_parameter_error(1010, "受权参数错误"),

	sys_authorize_error(1020, "受权错误"), sys_authorize_system_error(1021, "系统没有授权"),

	sys_authorize_code_type_error(1021, "授权类型错误"),

	sys_authorize_auth_code_error(1022, "授权码错误"),

	sys_authorize_get_tocken_error(1022, "获得Token错误"),

	sys_user_not_login_error(1030, "用户没有登录"),

	sys_user_login_parameter_error(1040, "用户登录参数错误"),

	sys_user_login_fail_error(1050, "用户登录失败"), sys_user_not_find_error(1051, "没有找到用户"), sys_user_name_passwd_error(1052, "用户名或密码错误"),

	error_search_user_menu_error(1053, "查询用户权限错误");

	private Integer errorCode;

	private String errorMeg;

	private RequestResultErrorMsg(Integer errorCode, String errorMeg) {
		this.errorCode = errorCode;
		this.errorMeg = errorMeg;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public String getErrorMeg() {
		return errorMeg;
	}

}
