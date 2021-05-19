package cn.oxo.iworks.networks.hclient;

import org.apache.http.Header;

import cn.oxo.iworks.networks.hclient.IHttpProtocolService.CharEncoded;

public class PostByteArrayMessageRequest extends IRequest {

      private byte[] sendMessage;
      /**
       * 是否对发送的内容进行编码
       */
      private boolean contentencoding = true;

      private GetPostHttpRequestResult requestResult;

      public PostByteArrayMessageRequest(CharEncoded charEncoded, boolean contentencoding) {
            super(charEncoded);
            this.contentencoding = contentencoding;

      }

      public PostByteArrayMessageRequest() {
            super(CharEncoded.UTF8);
            this.setContentencoding(false);

      }

      public GetPostHttpRequestResult getRequestResult() {
            return requestResult;
      }

      public void setRequestResult(GetPostHttpRequestResult requestResult) {
            this.requestResult = requestResult;
      }

      public boolean isContentencoding() {
            return contentencoding;
      }

      public void setContentencoding(boolean contentencoding) {
            this.contentencoding = contentencoding;
      }

      public byte[] getSendMessage() {
            return sendMessage;
      }

      public void setSendMessage(byte[] sendMessage) {
            this.sendMessage = sendMessage;
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
