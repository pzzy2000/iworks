package cn.oxo.iworks.web.shiro.bak;

import org.apache.shiro.authc.SimpleAuthenticationInfo;

public class ShiroClientAuthenticationInfo extends SimpleAuthenticationInfo {

      private LoginType loginType;

      public ShiroClientAuthenticationInfo() {
            super();
            // TODO Auto-generated constructor stub
      }

      public ShiroClientAuthenticationInfo(LoginType loginType, Object principal, Object credentials, String realmName) {
            super(principal, credentials, realmName);
            this.loginType = loginType;

      }

      public LoginType getLoginType() {
            return loginType;
      }

}
