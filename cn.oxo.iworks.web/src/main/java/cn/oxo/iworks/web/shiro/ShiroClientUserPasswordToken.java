package cn.oxo.iworks.web.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

public class ShiroClientUserPasswordToken extends UsernamePasswordToken {

      private ActionType action;

      private LoginType loginType;

      /**
      	 * 
      	 */
      private static final long serialVersionUID = 7544295776678876804L;

      private String openId;

      /**
       * 
       * @param username
       * @param password
       *              weixin code
       * @param loginType
       */
      public ShiroClientUserPasswordToken(String username, String password, ActionType action, LoginType loginType) {
            super(username, password);
            this.action = action;
            this.loginType = loginType;

      }

      public String getOpenId() {
            return openId;
      }

      public void setOpenId(String openId) {
            this.openId = openId;
      }

      public ActionType getAction() {
            return action;
      }

      public LoginType getLoginType() {
            return loginType;
      }

      public void setLoginType(LoginType loginType) {
            this.loginType = loginType;
      }

      public void setAction(ActionType action) {
            this.action = action;
      }

}
