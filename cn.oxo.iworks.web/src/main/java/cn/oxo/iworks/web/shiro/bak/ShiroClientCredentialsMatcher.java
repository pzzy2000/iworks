package cn.oxo.iworks.web.shiro.bak;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * 这里要做非对称解密
 * 
 * @author i
 *
 */

// http://localhost:8080/opt/login?token=42b3a275-f245-4707-8b62-3ec108c31a73&userId=root&secret=20bcd4e68d271910b5fc38a289f30dc4
public class ShiroClientCredentialsMatcher extends SimpleCredentialsMatcher {

      private Logger logger = LogManager.getLogger(ShiroClientCredentialsMatcher.class);

      @Override
      public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {

            ShiroClientUserPasswordToken iManagerPlatformUserPasswordToken = (ShiroClientUserPasswordToken) token;

            switch (iManagerPlatformUserPasswordToken.getAction()) {

                  case password: {
                        ShiroClientAuthenticationInfo iSimpleAuthenticationInfo = (ShiroClientAuthenticationInfo) info;

                        String userInfoName = (String) iSimpleAuthenticationInfo.getPrincipals().getPrimaryPrincipal();

                        String inPassword = ShiroUnits.createPassword(userInfoName, new String(iManagerPlatformUserPasswordToken.getPassword()));

                        if (inPassword.equals(iSimpleAuthenticationInfo.getCredentials())) {
                              return true;
                        }

                        return false;
                  }

                  default:
                        return false;
            }

      }

}
