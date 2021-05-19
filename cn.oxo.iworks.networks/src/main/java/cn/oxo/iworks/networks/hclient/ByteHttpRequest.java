package cn.oxo.iworks.networks.hclient;

import org.apache.http.Header;

import cn.oxo.iworks.networks.hclient.IHttpProtocolService.CharEncoded;

public class ByteHttpRequest extends ABGetPostRequest {

      private ByteHttpRequestResult byteHttpRequestResult;

      public ByteHttpRequest(CharEncoded charEncoded) {
            super(charEncoded);

      }

      public ByteHttpRequest() {
            super(CharEncoded.UTF8);

      }

      public ByteHttpRequestResult getByteHttpRequestResult() {
            return byteHttpRequestResult;
      }

      public void setByteHttpRequestResult(ByteHttpRequestResult byteHttpRequestResult) {
            this.byteHttpRequestResult = byteHttpRequestResult;
      }

      @Override
      public String getResponse() {
            // TODO Auto-generated method stub
            return null;
      }

      @Override
      public byte[] getResponseByte() {
            // TODO Auto-generated method stub
            return null;
      }

      @Override
      public Header[] getResponseHeaders() {
            // TODO Auto-generated method stub
            return null;
      }

      @Override
      public int getHttpCode() {
            // TODO Auto-generated method stub
            return 0;
      }

}
