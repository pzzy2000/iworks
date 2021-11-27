package cn.oxo.iworks.web.shiro.simple;

import cn.oxo.iworks.web.shiro.LoginType;

/*
 * 微信 acces openid; password openid
 */
public class ShiroClientInfoBean {

    private Long id;

    private LoginType loginType;

    private String userName;

    private String access;

    private String password;

    private String code;

    private String token;

    private String openId;

    public LoginType getLoginType () {
        return loginType;
    }

    public void setLoginType (LoginType loginType) {
        this.loginType = loginType;
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

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public String getCode () {
        return code;
    }

    public void setCode (String code) {
        this.code = code;
    }

    public String getToken () {
        return token;
    }

    public void setToken (String token) {
        this.token = token;
    }

    public String getUserName () {
        return userName;
    }

    public void setUserName (String userName) {
        this.userName = userName;
    }

    public String getOpenId () {
        return openId;
    }

    public void setOpenId (String openId) {
        this.openId = openId;
    }

}
