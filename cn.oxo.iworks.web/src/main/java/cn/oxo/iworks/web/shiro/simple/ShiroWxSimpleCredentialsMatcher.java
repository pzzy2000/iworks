package cn.oxo.iworks.web.shiro.simple;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

import cn.oxo.iworks.web.shiro.ShiroUnits;

public class ShiroWxSimpleCredentialsMatcher extends SimpleCredentialsMatcher {

    private Logger logger = LogManager.getLogger(ShiroWxSimpleCredentialsMatcher.class);

    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        ShiroIMbuyClientUserPasswordToken iManagerPlatformUserPasswordToken = (ShiroIMbuyClientUserPasswordToken)token;
        logger.info(
            "shiro simple credentials matcher :" + iManagerPlatformUserPasswordToken.getUserType() + " " + iManagerPlatformUserPasswordToken.getLoginType());
        switch (iManagerPlatformUserPasswordToken.getUserType()) {
            case client: {
                switch (iManagerPlatformUserPasswordToken.getLoginType()) {
                    case Wx: {
                        ShiroIMbuyClientAuthenticationInfo iSimpleAuthenticationInfo = (ShiroIMbuyClientAuthenticationInfo)info;

                        // 验证 openid
                        if (StringUtils.isEmpty(iSimpleAuthenticationInfo.getOpenId())) {
                         
                            return false;
                        }else {
                            iManagerPlatformUserPasswordToken.setOpenId(iSimpleAuthenticationInfo.getOpenId()); 
                        }
                        return true;
                    }
                    case Access: {
                        ShiroIMbuyClientAuthenticationInfo iSimpleAuthenticationInfo = (ShiroIMbuyClientAuthenticationInfo)info;
                        String password = ShiroUnits.createPassword(iManagerPlatformUserPasswordToken.getUsername(),iManagerPlatformUserPasswordToken.getPasswords());
                        if (password.equals(iSimpleAuthenticationInfo.getCode())) {
                            return true;
                        } else {
                            return false;
                        }

                    }
                   
                    default: {
                        return false;
                    }

                }

            }

            case manager: {
                ShiroIMbuyClientAuthenticationInfo iSimpleAuthenticationInfo = (ShiroIMbuyClientAuthenticationInfo)info;

                String userInfoName = (String)iSimpleAuthenticationInfo.getPrincipals().getPrimaryPrincipal();

                String inPassword = ShiroUnits.createPassword(userInfoName, new String(iManagerPlatformUserPasswordToken.getPassword()));

                if (inPassword.equals((String)iSimpleAuthenticationInfo.getCredentials())) {
                    return true;
                } else
                    return false;
            }

            default:
                return false;
        }

    }

    public static void main(String[] args) {
        String inPassword = ShiroUnits.createPassword("admin", "admin");

        System.out.println("> " + inPassword);
    }
}
