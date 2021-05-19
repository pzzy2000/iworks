package cn.oxo.iworks.networks.hclient;

import java.util.Map;
import java.util.Map.Entry;

public class HttpRequestActivity {

      /**
       * 绑定http GET 参数
       * 
       * @param params
       * @return
       */
      protected String buildParams(Map<String, String> params) {
            if (params == null || params.size() == 0)
                  return null;
            StringBuffer buffer = new StringBuffer();
            boolean isf = false;
            for (Entry<String, String> entry : params.entrySet()) {
                  if (!isf) {
                        buffer.append(entry.getKey() + "=" + entry.getValue());
                        isf = true;
                  } else {
                        buffer.append("&" + entry.getKey() + "=" + entry.getValue());
                  }
            }
            return buffer.toString();
      }

}
