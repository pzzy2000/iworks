package cn.oxo.iworks.networks.hclient;

import java.util.logging.Logger;

import org.apache.http.HttpStatus;

/**
 * http post 请求
 * 
 * @author you
 * 
 */
public class HttpPostRequestActivity extends HttpRequestResponseActivity<CharStringGetPostRequest> {

      protected Logger logger = Logger.getLogger(HttpPostRequestActivity.class.getName());

      public HttpPostRequestActivity() {
            super(new HttpProtocolService());

      }

      @Override
      public void doActive(String url, CharStringGetPostRequest request) throws HttpRequestServiceException {
            logger.info("do active url " + url);

            if (request instanceof CharStringGetPostRequest) {
                  CharStringGetPostRequest requestResponse = request;
                  IHttpProtocolService httpProtocolService = getHttpProtocolService();
                  GetPostHttpRequestResult requestResult = httpProtocolService.post(url, requestResponse);
                  requestResponse.setRequestResult(requestResult);
                  if (requestResult.getHttpCode() != HttpStatus.SC_OK) {
                        throw new HttpRequestServiceException("Request  Failure  status : " + requestResult.getHttpCode(), requestResult.getError());
                  }
            } else {
                  throw new HttpRequestServiceException(" " + request.getClass() + " error ! ");
            }

      }

}
