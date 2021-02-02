package cn.oxo.iworks.networks.hclient;

import java.util.logging.Logger;

/**
 * http post 方式发送 String 请求
 * 
 * @author you
 * 
 */
public class HttpPostByteArrayMessageRequestActivity extends HttpRequestResponseActivity<PostByteArrayMessageRequest> {

	protected Logger logger = Logger.getLogger(HttpPostByteArrayMessageRequestActivity.class.getName());
	
    public HttpPostByteArrayMessageRequestActivity(IHttpProtocolService httpProtocolService) {
        super( httpProtocolService);

    }

    public HttpPostByteArrayMessageRequestActivity() {
    	super( new HttpProtocolService());

    }

    @Override
    public void doActive(String url,PostByteArrayMessageRequest request) throws HttpRequestServiceException {
    	logger.info("do active url " +url);
	if (request instanceof PostByteArrayMessageRequest) {
	    PostByteArrayMessageRequest requestResponse = (PostByteArrayMessageRequest) request;
	    IHttpProtocolService httpProtocolService = getHttpProtocolService();
	    GetPostHttpRequestResult response = httpProtocolService.postByteArray(url, requestResponse);
	    requestResponse.setRequestResult(response);
//	    if (response.getHttpCode() != HttpStatus.SC_OK) {
//		throw new HttpRequestServiceException("Request  Failure  status : " + response.getHttpCode());
//	    } 
	} else {
	    throw new HttpRequestServiceException(" " + request.getClass() + " error !  PostSingleMessageRequest ");
	}

    }

}
