package cn.oxo.iworks.networks.hclient;

/**
 * HTTP 请求服务
 * 
 * @author you
 * 
 */
public interface IHttpRequestActivity<Bean extends IRequest> {
	
	public void doActive(String url, Bean request) throws HttpRequestServiceException;
	
}
