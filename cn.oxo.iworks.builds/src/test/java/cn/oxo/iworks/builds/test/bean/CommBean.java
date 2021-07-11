package cn.oxo.iworks.builds.test.bean;

import java.io.Serializable;

import cn.oxo.iworks.databases.annotation.Column;
import cn.oxo.iworks.databases.annotation.ColumnType;
import cn.oxo.iworks.databases.annotation.Id;

public abstract class CommBean implements Serializable {

	/** 
	 *  
	 */
	private static final long serialVersionUID = -6553404787101957561L;
	@Id
	@Column(name = "id", columnType = ColumnType.BIGINTLong, length = 20, isCanNull = false, desc = "主建")
	// @ApiModelProperty(name = "bean.id")
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
