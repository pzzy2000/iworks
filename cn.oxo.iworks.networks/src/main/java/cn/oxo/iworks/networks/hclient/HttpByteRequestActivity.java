package cn.oxo.iworks.networks.hclient;
//package org.cn.oxo.e.commerce.common.net;
//
///**
// * byte 文件请求
// * 
// * @author you
// * 
// */
//
//public class HttpByteRequestActivity extends HttpRequestResponseActivity<ByteHttpRequest> {
//
//    public HttpByteRequestActivity(IHttpProtocolService httpProtocolService, String url) {
//        super( httpProtocolService, url );
//        // TODO Auto-generated constructor stub
//    }
//
//    public HttpByteRequestActivity(String url) {
//        super( new HttpProtocolService(), url );
//
//    }
//
//    @Override
//    public void doActive(ByteHttpRequest request) throws HttpRequestServiceException {
//
//    	logger.info("do active url " + getUrl() );
//
//        if (request instanceof ByteHttpRequest) {
//            ByteHttpRequest requestResult = (ByteHttpRequest) request;
//            IHttpProtocolService httpProtocolService = getHttpProtocolService();
//            ByteHttpRequestResult response = httpProtocolService.getByteArrayInputStream( url, requestResult );
//            requestResult.setByteHttpRequestResult( response );
//        } else {
//            throw new HttpRequestServiceException( " " + request.getClass() + " error !  PostSingleMessageRequest " );
//        }
//
//    }
//
//}
