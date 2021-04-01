package cn.oxo.iworks.networks.hclient;


import java.util.logging.Logger;

import org.apache.http.HttpStatus;

/**
 * http post 请求
 * 
 * @author you
 * 
 */
public class HttpUploadFileRequestActivity extends HttpRequestResponseActivity<CharStringGetPostRequest> {

	protected Logger logger = Logger.getLogger(HttpUploadFileRequestActivity.class.getName());

    public HttpUploadFileRequestActivity() {
        super( new HttpProtocolService() );

    }

    @Override
    public void doActive(String url,CharStringGetPostRequest request) throws HttpRequestServiceException {
    	logger.info( "do active url " + url );

        if (request instanceof PostUploadFileRequest) {
            PostUploadFileRequest requestResponse = (PostUploadFileRequest) request;
            IHttpProtocolService httpProtocolService = getHttpProtocolService();
            GetPostHttpRequestResult requestResult = httpProtocolService.upload( url, requestResponse );
            requestResponse.setRequestResult( requestResult );
            if(requestResult.getHttpCode()!=HttpStatus.SC_OK){
                throw new HttpRequestServiceException("Request  Failure  status : " + requestResult.getHttpCode(), requestResult.getError());
            }
        } else {
            throw new HttpRequestServiceException( " " + request.getClass() + " error ! " );
        }

    }

}
