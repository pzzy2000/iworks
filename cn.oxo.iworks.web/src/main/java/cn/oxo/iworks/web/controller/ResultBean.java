package cn.oxo.iworks.web.controller;

//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;

//@ApiModel(value = "返回结果")
public class ResultBean<V> {

//	 @ApiModelProperty(value="结果代码",notes="0:正常")
	private int code;
//	 @ApiModelProperty(value="结果")
	private V result;
//	 @ApiModelProperty(value="错误消息",notes="code !=0 显示")
	private String msg;

	public ResultBean(int code, V result) {
		super();
		this.code = code;
		this.result = result;
	}

	public ResultBean(int code, String msg, V result) {
		super();
		this.code = code;
		this.msg = msg;
		this.result = result;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public V getResult() {
		return result;
	}

	public void setResult(V result) {
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
