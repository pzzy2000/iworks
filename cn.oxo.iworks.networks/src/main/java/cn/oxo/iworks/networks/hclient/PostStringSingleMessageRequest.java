package cn.oxo.iworks.networks.hclient;

import cn.oxo.iworks.networks.hclient.IHttpProtocolService.CharEncoded;

public class PostStringSingleMessageRequest extends PostSingleMessageRequest {

      private String sendMessage;

      public PostStringSingleMessageRequest(String sendMessage) {
            this.sendMessage = sendMessage;

      }

      public PostStringSingleMessageRequest(String sendMessage, CharEncoded charEncoded, boolean contentencoding) {
            super(charEncoded, contentencoding);
            this.sendMessage = sendMessage;
      }

      @Override
      public String getSendMessage() {

            return sendMessage;
      }

}
