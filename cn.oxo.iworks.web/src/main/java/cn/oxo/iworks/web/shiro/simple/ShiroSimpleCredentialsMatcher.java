package cn.oxo.iworks.web.shiro.simple;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

public class ShiroSimpleCredentialsMatcher extends SimpleCredentialsMatcher {

    private Logger logger = LogManager.getLogger(ShiroSimpleCredentialsMatcher.class);

    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        ShiroClientUserPasswordToken iManagerPlatformUserPasswordToken = (ShiroClientUserPasswordToken)token;
        logger.info(
            "shiro simple credentials matcher :" + iManagerPlatformUserPasswordToken.getUserType() + " " + iManagerPlatformUserPasswordToken.getLoginType());
        switch (iManagerPlatformUserPasswordToken.getUserType()) {
            case client: {
                switch (iManagerPlatformUserPasswordToken.getLoginType()) {
                    case Wx: {
                        ShiroClientAuthenticationInfo iSimpleAuthenticationInfo = (ShiroClientAuthenticationInfo)info;

                        // 验证 openid
                        if (StringUtils.isEmpty(iSimpleAuthenticationInfo.getOpenId())) {
                         
                            return false;
                        }else {
                            iManagerPlatformUserPasswordToken.setOpenId(iSimpleAuthenticationInfo.getOpenId()); 
                        }
                        return true;
                    }
                    case Access: {
                        ShiroClientAuthenticationInfo iSimpleAuthenticationInfo = (ShiroClientAuthenticationInfo)info;
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
                ShiroClientAuthenticationInfo iSimpleAuthenticationInfo = (ShiroClientAuthenticationInfo)info;

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
