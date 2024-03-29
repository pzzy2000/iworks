package cn.oxo.iworks.web.shiro.simple;

import cn.oxo.iworks.web.shiro.LoginType;

public class ClientUserLoginBean {

    private LoginType loginType;
    
    private String  sysCode;

    // user
    private String access;

    private String password;

    // 微信
    private String code;

    private String userName;

    private String avatarUrl;

    private String openId;

    public String getCode () {
        return code;
    }

    public void setCode (String code) {
        this.code = code;
    }

    public String getUserName () {
        return userName;
    }

    public void setUserName (String userName) {
        this.userName = userName;
    }

    public String getAvatarUrl () {
        return avatarUrl;
    }

    public void setAvatarUrl (String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getAccess () {
        return access;
    }

    public void setAccess (String access) {
        this.access = access;
    }

    public String getPassword () {
        return password;
    }

    public void setPassword (String password) {
        this.password = password;
    }

    
    public LoginType getLoginType () {
        return loginType;
    }

    public void setLoginType (LoginType loginType) {
        this.loginType = loginType;
    }

    public String getOpenId () {
        return openId;
    }

    public void setOpenId (String openId) {
        this.openId = openId;
    }

    public String getSysCode () {
        return sysCode;
    }

    public void setSysCode (String sysCode) {
        this.sysCode = sysCode;
    }

}
