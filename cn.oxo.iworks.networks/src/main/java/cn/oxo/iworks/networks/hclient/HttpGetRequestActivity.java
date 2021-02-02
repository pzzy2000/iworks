package cn.oxo.iworks.networks.hclient;

import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;


/**
 * http Get 服务
 * 
 * @author you
 * 
 */
public class HttpGetRequestActivity extends HttpRequestResponseActivity<CharStringGetPostRequest> {
	protected Logger logger = Logger.getLogger(HttpGetRequestActivity.class.getName());
    public HttpGetRequestActivity(HttpProtocolService httpProtocolService) {
        super( httpProtocolService);
    }
 
    public HttpGetRequestActivity() {
        super( new HttpProtocolService());
    }

    @Override
    public void doActive(String url ,CharStringGetPostRequest request) throws HttpRequestServiceException {
    	logger.info( "do active url " +url + "  request " + request.getCharEncoded() );

        if (request instanceof CharStringGetPostRequest) {
            CharStringGetPostRequest requestResponse = (CharStringGetPostRequest) request;
            IHttpProtocolService httpProtocolService = getHttpProtocolService();
            String prams = buildParams( requestResponse.getRequestParams() );
            boolean isc = url.contains( "?" );
            if (isc) {
                if (!StringUtils.isEmpty( prams ))
                    url = url + "&" + prams;
            } else {
                if (!StringUtils.isEmpty( prams ))
                    url = url + "?" + prams;
            }
            GetPostHttpRequestResult requestResult = httpProtocolService.get( url, requestResponse );
            requestResponse.setRequestResult( requestResult );
            
            if(requestResult.getHttpCode()!= HttpStatus.SC_OK){
        	 throw new HttpRequestServiceException("Request  Failure  status : " + requestResult.getHttpCode());
            }
        } else {
            throw new HttpRequestServiceException( " " + request.getClass() + " error ! " );
        }
 
    } 

}
