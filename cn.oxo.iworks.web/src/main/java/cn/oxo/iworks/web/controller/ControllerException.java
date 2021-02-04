package cn.oxo.iworks.web.controller;

public class ControllerException extends Exception {

	private int errorCode;

	private String errorMsg;

	public ControllerException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public ControllerException(int errorCode, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public String getErrMsgCode() {
		return errorMsg + "[" + errorCode + "]";
	}

}
