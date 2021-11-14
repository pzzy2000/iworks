package cn.oxo.iworks.web.shiro.simple;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.alibaba.fastjson.JSON;

import cn.oxo.imibuying.commons.params.IMBuySysType;
import cn.oxo.iworks.databases.SystemOptServiceException;
import cn.oxo.iworks.web.LoginSessionBean;
import cn.oxo.iworks.web.UserType;
import cn.oxo.iworks.web.shiro.ClientInfoBean;
import cn.oxo.iworks.web.shiro.LoginType;

public class ShiroWxClientAuthorizingRealm extends AuthorizingRealm {

    private Logger logger = LogManager.getFormatterLogger(ShiroWxClientAuthorizingRealm.class);

    private IShiroExClientService shiroExClientService;

    public ShiroWxClientAuthorizingRealm(CredentialsMatcher credentialsMatcher, IShiroExClientService shiroExClientService) {
        super(credentialsMatcher);
        this.shiroExClientService = shiroExClientService;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo (PrincipalCollection principals) {

        String useraccess = (String)principals.getPrimaryPrincipal();

        this.logger.info(">>>>>>>>>>>>>>>  get authorization info primary principal  : user  access  " + useraccess);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        LoginSessionBean loginBean = (LoginSessionBean)SecurityUtils.getSubject().getSession().getAttribute("key_session_login_user");
        if (loginBean == null) {
            return info;
        }
        info.addRole("test");
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo (AuthenticationToken token) throws AuthenticationException {

        ShiroIMbuyClientUserPasswordToken platformToken = (ShiroIMbuyClientUserPasswordToken)token;
        // String access = (String)platformToken.getPrincipal();
        switch (platformToken.getUserType()) {
            case manager:
                return manager(platformToken, platformToken);
            case client:
                return client(platformToken, platformToken);
            default:
                throw new AuthenticationException("登录错误[用户类型不对]");
        }

    }

    private AuthenticationInfo manager (ShiroIMbuyClientUserPasswordToken platformToken, AuthenticationToken token) throws AuthenticationException {

        String access = (String)platformToken.getPrincipal();
        try {
            ClientInfoBean iClientInfoBean = shiroExClientService.loginByManager(access);
            if (iClientInfoBean == null) {
                throw new AuthenticationException("登录错误[没发现相关人员]");
            }

            ShiroIMbuyClientAuthenticationInfo iShiroWxClientAuthenticationInfo = new ShiroIMbuyClientAuthenticationInfo(iClientInfoBean.getAccess(), iClientInfoBean.getPassword(), getName());
            iShiroWxClientAuthenticationInfo.setUserType(UserType.manager);
            return iShiroWxClientAuthenticationInfo;
        } catch (SystemOptServiceException e) {
            logger.error(e.getMessage(), e);
            throw new AuthenticationException(e.getMessage());
        }

    }

    private AuthenticationInfo client (ShiroIMbuyClientUserPasswordToken platformToken, AuthenticationToken token) throws AuthenticationException {
        String access = (String)platformToken.getPrincipal();
        switch (platformToken.getLoginType()) {

            case Access: {
                try {
                    ClientUserLoginBean iWxUserInfoBean = new ClientUserLoginBean();
                    iWxUserInfoBean.setUserName(access);
                    iWxUserInfoBean.setType(platformToken.getLoginType());
                    iWxUserInfoBean.setCode(new String(platformToken.getPassword()));
                    iWxUserInfoBean.setType(cn.oxo.imibuying.commons.params.LoginType.Access);
                    iWxUserInfoBean.setSysType(platformToken.getBuySysType());
                    ShiroWxClientInfoBean iClientInfoBean = shiroExClientService.loginByClient(iWxUserInfoBean);
                    ShiroIMbuyClientAuthenticationInfo iShiroWxClientAuthenticationInfo = new ShiroIMbuyClientAuthenticationInfo(platformToken.getLoginType(), iClientInfoBean.getAccess(), iClientInfoBean.getPassword(), getName());
                    iShiroWxClientAuthenticationInfo.setCode(iClientInfoBean.getCode());
                    iShiroWxClientAuthenticationInfo.setId(iClientInfoBean.getId().toString());
                    iShiroWxClientAuthenticationInfo.setUserType(UserType.client);
                    
                    return iShiroWxClientAuthenticationInfo;

                } catch (SystemOptServiceException e) {
                    logger.error(e.getMessage(), e);
                    throw new AuthenticationException(e.getMessage());
                }
            }
            case Wx: {
                try {
                    ClientUserLoginBean iWxUserInfoBean = new ClientUserLoginBean();
                    iWxUserInfoBean.setUserName(platformToken.getPlatformUserName());
                    iWxUserInfoBean.setAvatarUrl(platformToken.getAvatarUrl());
                    iWxUserInfoBean.setCode(platformToken.getCode());  
                    iWxUserInfoBean.setType(cn.oxo.imibuying.commons.params.LoginType.Wx);
                    iWxUserInfoBean.setSysType(platformToken.getBuySysType());
                    ShiroWxClientInfoBean iClientInfoBean = shiroExClientService.loginByClient(iWxUserInfoBean);
                    ShiroIMbuyClientAuthenticationInfo iShiroWxClientAuthenticationInfo = new ShiroIMbuyClientAuthenticationInfo(platformToken.getLoginType(), iClientInfoBean.getOpenId(), iClientInfoBean.getOpenId(), getName());
                    iShiroWxClientAuthenticationInfo.setCode(iClientInfoBean.getCode());
                    iShiroWxClientAuthenticationInfo.setOpenId(iClientInfoBean.getOpenId());
                    iShiroWxClientAuthenticationInfo.setId(iClientInfoBean.getId().toString());
                    iShiroWxClientAuthenticationInfo.setUserType(UserType.client);
                    return iShiroWxClientAuthenticationInfo;

                } catch (SystemOptServiceException e) {
                    logger.error(e.getMessage(), e);
                    throw new AuthenticationException(e.getMessage());
                }

            }
            default: {
                throw new AuthenticationException("登录错误[不支持登录方式]");
            }
        }
    }

}
