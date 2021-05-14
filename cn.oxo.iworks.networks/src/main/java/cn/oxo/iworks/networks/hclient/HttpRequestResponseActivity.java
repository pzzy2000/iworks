package cn.oxo.iworks.networks.hclient;

public abstract class HttpRequestResponseActivity<Bean extends IRequest> extends HttpRequestActivity implements IHttpRequestActivity<Bean> {
	
	protected IHttpProtocolService httpProtocolService;
	
	public HttpRequestResponseActivity(IHttpProtocolService httpProtocolService) {
		
		this.httpProtocolService = httpProtocolService;
	}
	
	protected IHttpProtocolService getHttpProtocolService() {
		return httpProtocolService;
	}
	
}
