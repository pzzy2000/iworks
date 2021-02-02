package cn.oxo.iworks.networks.hclient;

import java.util.logging.Logger;

import org.apache.http.HttpStatus;

/**
 * http post 方式发送 String 请求
 * 
 * @author you
 * 
 */
public class HttpPostSingleMessageRequestActivity extends HttpRequestResponseActivity<PostSingleMessageRequest> {

	protected Logger logger = Logger.getLogger(HttpPostSingleMessageRequestActivity.class.getName());
	

	public HttpPostSingleMessageRequestActivity() {
		super(new HttpProtocolService());

	}

	@Override
	public void doActive(String url,PostSingleMessageRequest request) throws HttpRequestServiceException {
		logger.info("do active url " + url);
		if (request instanceof PostSingleMessageRequest) {
			PostSingleMessageRequest requestResponse = (PostSingleMessageRequest) request;
			IHttpProtocolService httpProtocolService = getHttpProtocolService();
			GetPostHttpRequestResult response = httpProtocolService.postSingleMessage(url, requestResponse);
			requestResponse.setRequestResult(response);
			if (response.getHttpCode() != HttpStatus.SC_OK) {
				throw new HttpRequestServiceException("Request  Failure  status : " + response.getHttpCode()
						+ " error : " + requestResponse.getRequestResult().getError());
			}
		} else {
			throw new HttpRequestServiceException(" " + request.getClass() + " error !  PostSingleMessageRequest ");
		}

	}

}
