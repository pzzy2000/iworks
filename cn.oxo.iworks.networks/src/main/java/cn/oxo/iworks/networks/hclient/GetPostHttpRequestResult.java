package cn.oxo.iworks.networks.hclient;

import org.apache.http.Header;

public class GetPostHttpRequestResult extends HttpRequestResult {
	
	private String response;
	
	private String error;
	
	private int httpCode;
	
	private byte[] responseByte;
	
	public String searchHeader(String key) {
		if (this.getHeaders() == null)
			return null;
		for (Header iHeader : getHeaders()) {
			if (iHeader.getName().equals(key)) {
				return iHeader.getValue();
			}
		}
		return null;
	}
	
	public String getResponse() {
		return response;
	}
	
	public void setResponse(String response) {
		this.response = response;
	}
	
	public String getError() {
		return error;
	}
	
	public void setError(String error) {
		this.error = error;
	}
	
	public int getHttpCode() {
		return httpCode;
	}
	
	public void setHttpCode(int httpCode) {
		this.httpCode = httpCode;
	}
	
	public byte[] getResponseByte() {
		return responseByte;
	}
	
	public void setResponseByte(byte[] responseByte) {
		this.responseByte = responseByte;
	}
	
}
