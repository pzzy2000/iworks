package cn.oxo.iworks.networks.hclient;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.cookie.Cookie;

/**
 * http 请求结果
 * 
 * @author you
 * 
 */
public abstract class HttpRequestResult {

      private Set<Cookie> cookie = new HashSet<Cookie>();

      private Header[] headers;

      public Header[] getHeaders() {
            return headers;
      }

      public Map<String, String> getHeadersByMap() {
            Map<String, String> xx = new HashMap<String, String>();
            for (Header header : headers) {
                  xx.put(header.getName(), header.getValue());
            }
            return xx;
      }

      public void setHeaders(Header[] headers) {
            this.headers = headers;
      }

      public Set<Cookie> getCookie() {
            return cookie;
      }

      public void setCookie(Set<Cookie> cookie) {
            this.cookie = cookie;
      }

}
