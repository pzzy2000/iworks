package cn.oxo.iworks.networks.hclient;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;

import cn.oxo.iworks.networks.hclient.IHttpProtocolService.CharEncoded;

public abstract class IRequest {

      protected CharEncoded charEncoded;

      /**
       * 请求的Cookie
       */
      private Map<String, Cookie> reqCookie = new HashMap<String, Cookie>();

      public Map<String, String> header = new HashMap<String, String>();

      public IRequest(CharEncoded charEncoded) {
            super();
            this.charEncoded = charEncoded;
      }

      public CharEncoded getCharEncoded() {
            return charEncoded;
      }

      public Map<String, Cookie> getReqCookie() {
            return reqCookie;
      }

      public void setReqCookie(Map<String, Cookie> reqCookie) {
            this.reqCookie = reqCookie;
      }

      public void addReqCookie(Map<String, String> reqCookie) {
            for (Map.Entry<String, String> entry : reqCookie.entrySet()) {
                  this.reqCookie.put(entry.getKey(), new BasicClientCookie(entry.getKey(), entry.getValue()));
            }

      }

      private String url;

      public String getUrl() {
            return url;
      }

      public void setUrl(String url) {
            this.url = url;
      }

      public Map<String, String> getHeader() {
            return header;
      }

      public void setHeader(Map<String, String> header) {
            this.header = header;
      }

      public abstract String getResponse();

      public abstract byte[] getResponseByte();

      public abstract Header[] getResponseHeaders();

      public abstract int getHttpCode();

}
