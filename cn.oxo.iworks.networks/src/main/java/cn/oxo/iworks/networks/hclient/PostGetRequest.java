package cn.oxo.iworks.networks.hclient;

import cn.oxo.iworks.networks.hclient.IHttpProtocolService.CharEncoded;

/**
 * 请求应答
 * 
 * @author you
 * 
 */
public class PostGetRequest extends CharStringGetPostRequest {

      public PostGetRequest(CharEncoded charEncoded) {
            super(charEncoded);

      }

      public PostGetRequest() {
            super(CharEncoded.UTF8);

      }

}
