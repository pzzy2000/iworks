package cn.oxo.iworks.web;

import com.fasterxml.jackson.annotation.JsonFormat;

//@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum UserType {
	manager("manager", "管理员", "管理员"),

	client("client", "客户", "客户");

	private String code;

	private String name;

	private String sysPlatform;

	private UserType(String code, String name, String sysPlatform) {
		this.code = code;
		this.name = name;
		this.sysPlatform = sysPlatform;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public String getSysPlatform() {
		return sysPlatform;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSysPlatform(String sysPlatform) {
		this.sysPlatform = sysPlatform;
	}

	public static UserType getBycode(String code) {
		for (UserType userType : UserType.values()) {
			if (userType.getCode().equals(code)) {
				return userType;
			}
		}
		return null;
	}
}
