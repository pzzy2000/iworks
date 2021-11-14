package cn.oxo.iworks.web.shiro.bak;

import org.apache.shiro.authc.AuthenticationException;

public class ShiroClientAuthenticationException extends AuthenticationException {

      private static final long serialVersionUID = 557922698548503614L;

      private Integer code;

      private String openId;

      public ShiroClientAuthenticationException() {
            super();

      }

      public ShiroClientAuthenticationException(String openId, Integer code, String explanation) {
            super(explanation);
            this.code = code;
            this.openId = openId;
      }

      public Integer getCode() {
            return code;
      }

      public String getOpenId() {
            return openId;
      }

}
