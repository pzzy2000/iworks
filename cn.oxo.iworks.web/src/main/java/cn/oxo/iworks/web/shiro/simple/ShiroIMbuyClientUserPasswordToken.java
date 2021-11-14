package cn.oxo.iworks.web.shiro.simple;

import org.apache.shiro.authc.UsernamePasswordToken;

import cn.oxo.imibuying.commons.params.IMBuySysType;
import cn.oxo.imibuying.commons.params.LoginType;
import cn.oxo.iworks.web.UserType;

public class ShiroIMbuyClientUserPasswordToken extends UsernamePasswordToken {

    private static final long serialVersionUID = 7544295776678876804L;

    private LoginType loginType;

    private UserType userType;

    private String access;

    private String avatarUrl;

    private String platformUserName;
    
    private String openId;
    
    private IMBuySysType buySysType;
  
    public ShiroIMbuyClientUserPasswordToken(String access, String password) {
        super(access, password, true);
        this.access = access;
        loginType = LoginType.Access;
    }

    public ShiroIMbuyClientUserPasswordToken(String platformUserName,String code, String avatarUrl) {
        super(code, code, true);
        this.avatarUrl = avatarUrl;
        this.platformUserName = platformUserName;
       

        loginType = LoginType.Wx;

    }

    public LoginType getLoginType () {
        return loginType;
    }

    public void setLoginType (LoginType loginType) {
        this.loginType = loginType;
    }

    public UserType getUserType () {
        return userType;
    }

    public void setUserType (UserType userType) {
        this.userType = userType;
    }

    public String getAccess () {
        return access;
    }

    public void setAccess (String access) {
        this.access = access;
    }

    public String getAvatarUrl () {
        return avatarUrl;
    }

    public void setAvatarUrl (String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

   
    public String getCode () {
        return this.getUsername();
    }

    public String getPasswords () {
        return new String(getPassword());
    }

    public String getPlatformUserName () {
        return platformUserName;
    }

    public String getOpenId () {
        return openId;
    }

    public void setOpenId (String openId) {
        this.openId = openId;
    }

    public IMBuySysType getBuySysType () {
        return buySysType;
    }

    public void setBuySysType (IMBuySysType buySysType) {
        this.buySysType = buySysType;
    }

    
}
