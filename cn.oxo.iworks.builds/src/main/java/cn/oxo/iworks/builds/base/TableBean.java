package cn.oxo.iworks.builds.base;

import java.util.List;

public class TableBean {

	private String name;
	
	private String desc;
	
	private Class<?> pojo;

	private ColumnBean idColumn;

	private List<ColumnBean> columnBeans;
	
	private String action;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ColumnBean getIdColumn() {
		return idColumn;
	}

	public void setIdColumn(ColumnBean idColumn) {
		this.idColumn = idColumn;
	}

	public List<ColumnBean> getColumnBeans() {
		return columnBeans;
	}

	public void setColumnBeans(List<ColumnBean> columnBeans) {
		this.columnBeans = columnBeans;
	}

	public Class<?> getPojo() {
		return pojo;
	}

	public void setPojo(Class<?> pojo) {
		this.pojo = pojo;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

}
