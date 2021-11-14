package cn.oxo.iworks.web.controller;

import cn.oxo.iworks.web.shiro.bak.ActionType;
import cn.oxo.iworks.web.shiro.bak.LoginType;

public class LoginInfoBean {

      private String access;

      private String password;

      private ActionType action;

      private LoginType logintype;

      public String getAccess() {
            return access;
      }

      public void setAccess(String access) {
            this.access = access;
      }

      public String getPassword() {
            return password;
      }

      public void setPassword(String password) {
            this.password = password;
      }

      public ActionType getAction() {
            return action;
      }

      public void setAction(ActionType action) {
            this.action = action;
      }

      public LoginType getLogintype() {
            return logintype;
      }

      public void setLogintype(LoginType logintype) {
            this.logintype = logintype;
      }

      @Override
      public String toString() {
            return "LoginInfoBean [access=" + access + ", password=" + password + ", action=" + action + ", logintype=" + logintype + "]";
      }

}
