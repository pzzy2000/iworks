package cn.oxo.iworks.builds.test.bean;

import cn.oxo.iworks.databases.annotation.Column;
import cn.oxo.iworks.databases.annotation.ColumnType;
import cn.oxo.iworks.databases.annotation.Table;

@Table(name = "customer", desc = "顾客表", action = "/business/customer")

public class CustomerBean extends CommBean {

	@Column(name = "name", columnType = ColumnType.VARCHAR, length = 100, isCanNull = false, desc = "客户名称")
	private String name;

	@Column(name = "wx_name", columnType = ColumnType.VARCHAR, length = 100, isCanNull = false, desc = "微信名称")
	private String wxName;

	@Column(name = "open_id", columnType = ColumnType.VARCHAR, length = 100, isCanNull = false, desc = "openid")
	private String openId;

	@Column(name = "wk_id", columnType = ColumnType.VARCHAR, length = 20, isCanNull = false, desc = "第三方系统Id")
	private String wkId;

	@Column(name = "faces", columnType = ColumnType.VARCHAR, length = 300, isCanNull = true, desc = "头像")
	private String faces;

	@Column(name = "token", columnType = ColumnType.VARCHAR, length = 300, isCanNull = true, desc = "token", isCache = false)
	private String token;

	private Long busAgentId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWxName() {
		return wxName;
	}

	public void setWxName(String wxName) {
		this.wxName = wxName;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getWkId() {
		return wkId;
	}

	public void setWkId(String wkId) {
		this.wkId = wkId;
	}

	public String getFaces() {
		return faces;
	}

	public void setFaces(String faces) {
		this.faces = faces;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getBusAgentId() {
		return busAgentId;
	}

	public void setBusAgentId(Long busAgentId) {
		this.busAgentId = busAgentId;
	}

}
