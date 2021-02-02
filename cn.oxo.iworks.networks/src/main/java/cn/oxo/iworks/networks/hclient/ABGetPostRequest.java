package cn.oxo.iworks.networks.hclient;

import java.util.HashMap;
import java.util.Map;

import cn.oxo.iworks.networks.hclient.IHttpProtocolService.CharEncoded;

/**
 * 请求
 * 
 * @author you
 * 
 */
public abstract class ABGetPostRequest extends IRequest {

	

	private Map<String, String> requestParams = new HashMap<String, String>();

	public ABGetPostRequest(CharEncoded charEncoded) {
		super(charEncoded);

	}

	public void addAllParams(Map<String, String> requestParams) {
		requestParams.putAll(requestParams);
	}

	public void addRequestParams(String key, String value) {
		requestParams.put(key, value);
	}

	public Map<String, String> getHeader() {
		return header;
	}

	public void setHeader(Map<String, String> header) {
		this.header = header;
	}

	public Map<String, String> getRequestParams() {
		return requestParams;
	}

	public void setRequestParams(Map<String, String> requestParams) {
		this.requestParams = requestParams;
	}

	
	
	

}
