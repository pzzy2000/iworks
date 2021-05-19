package cn.oxo.iworks.networks.hclient;

import org.apache.http.Header;

import cn.oxo.iworks.networks.hclient.IHttpProtocolService.CharEncoded;

/**
 * 瀛楃涓�
 * 
 * @author you
 * 
 */
public class CharStringGetPostRequest extends ABGetPostRequest {

      private GetPostHttpRequestResult requestResult;

      public CharStringGetPostRequest(CharEncoded charEncoded) {
            super(charEncoded);

      }

      public GetPostHttpRequestResult getRequestResult() {
            return requestResult;
      }

      public void setRequestResult(GetPostHttpRequestResult requestResult) {
            this.requestResult = requestResult;
      }

      @Override
      public String getResponse() {
            // TODO Auto-generated method stub
            return requestResult.getResponse();
      }

      @Override
      public byte[] getResponseByte() {
            // TODO Auto-generated method stub
            return requestResult.getResponseByte();
      }

      @Override
      public Header[] getResponseHeaders() {
            // TODO Auto-generated method stub
            return requestResult.getHeaders();
      }

      @Override
      public int getHttpCode() {
            // TODO Auto-generated method stub
            return requestResult.getHttpCode();
      }

}
