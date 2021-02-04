package cn.oxo.iworks.web.shiro;

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

import cn.oxo.iworks.databases.SystemOptServiceException;
import cn.oxo.iworks.web.LoginSessionBean;

public class ShiroClientAuthorizingRealm extends AuthorizingRealm {

	private Logger logger = LogManager.getFormatterLogger(ShiroClientAuthorizingRealm.class);

//	@Autowired
//	@Qualifier(IAopSysManagerUserService.name)
//	private IAopSysManagerUserService userService;
//	

	private IShrioClientService clientService;

	//

	public ShiroClientAuthorizingRealm(CredentialsMatcher matcher, IShrioClientService clientService) {
		super(matcher);
		this.clientService = clientService;

	}

	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

		String useraccess = (String) principalCollection.getPrimaryPrincipal();

		logger.info(">>>>>>>>>>>>>>>  get authorization info primary principal  : user  access  " + useraccess);

		// 权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

		LoginSessionBean loginBean = (LoginSessionBean) SecurityUtils.getSubject().getSession().getAttribute("key_session_login_user");

		if (loginBean == null) {
			return info;
		} else {
			info.addRole("test");
			return info;
		}

	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

		ShiroClientUserPasswordToken platformToken = (ShiroClientUserPasswordToken) authenticationToken;

		switch (platformToken.getLoginType()) {

		case client: {
			String access = (String) authenticationToken.getPrincipal();
			logger.info("get authentication info authenticationToken  login type " + platformToken.getLoginType() + "  login : " + access);
			try {
				ClientInfoBean iSysClient = clientService.searchClient(access, platformToken.getLoginType());
				if (iSysClient == null) {
					throw new AuthenticationException("登录验证错误,没有发现相关账号");
				} else {
					return new ShiroClientAuthenticationInfo(platformToken.getLoginType(), iSysClient.getAccess(), iSysClient.getPassword(),
							getName());
				}
			} catch (SystemOptServiceException e) {
				throw new AuthenticationException("登录验证错误,没有发现相关账号");
			}

		}

		case manager: {
			String access = (String) authenticationToken.getPrincipal();
			logger.info("get authentication info authenticationToken  login type " + platformToken.getLoginType() + "  login : " + access);

			try {
				ClientInfoBean iSysClient = clientService.searchClient(access, platformToken.getLoginType());
				if (iSysClient == null) {
					throw new AuthenticationException("登录验证错误,没有发现相关账号");
				} else {
					return new ShiroClientAuthenticationInfo(platformToken.getLoginType(), iSysClient.getAccess(), iSysClient.getPassword(),
							getName());
				}
			} catch (SystemOptServiceException e) {
				throw new AuthenticationException("登录验证错误,没有发现相关账号");
			}

		}
		default:
			throw new AuthenticationException("登录验证错误,没有发现相关账号");
		}

	}

}
