package cn.oxo.iworks.web.shiro.simple;

import org.apache.shiro.authc.SimpleAuthenticationInfo;

import cn.oxo.iworks.web.UserType;
import cn.oxo.iworks.web.shiro.LoginType;

public class ShiroClientAuthenticationInfo extends SimpleAuthenticationInfo {

    private LoginType loginType;

    private String openId;

    private String code;

    private String id;

    private UserType userType;
    
    private String token;

    public ShiroClientAuthenticationInfo() {}

    public ShiroClientAuthenticationInfo(LoginType loginType,  String realmName) {
        super(null, null, realmName);
        this.loginType = loginType;
       
    }

    public ShiroClientAuthenticationInfo(LoginType loginType, Object principal, Object credentials, String realmName) {
        super(principal, credentials, realmName);
        this.loginType = loginType;
      
    }

    public ShiroClientAuthenticationInfo(Object principal, Object credentials, String realmName) {
        super(principal, credentials, realmName);

    }

    public LoginType getLoginType() {
        return this.loginType;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
