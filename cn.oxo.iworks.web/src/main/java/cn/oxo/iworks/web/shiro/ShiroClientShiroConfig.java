package cn.oxo.iworks.web.shiro;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.github.streamone.shiro.cache.RedissonShiroCacheManager;
import com.github.streamone.shiro.session.RedissonSessionDao;
import com.github.streamone.shiro.session.RedissonWebSessionManager;


//@Configuration
/**
 * 
 * @author Administrator
 *
 */
public abstract  class ShiroClientShiroConfig {

	private static Logger logger = LogManager.getLogger(ShiroClientShiroConfig.class);

	public static String header_key_auth = "auth";

	@Autowired
	private Environment env;
	
	/**
	 *  anon : 不认证
	 *  ClientAuthc :需要登录认证
	 *  ClientRoles:角色认证
	 *         列子 ：
	 *  put("/sys/login", "CrawlerAuthc") 
	 *  .put("/**", "CrawlerAuthc,ClientRoles[test]")
	 * @return
	 */
	
	/*
	 * 
	 */
	protected abstract  Map<String, String> filterChain();

	/**
	 * 允许跨域调用的过滤器
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.addAllowedOrigin("*");
		config.setAllowCredentials(true);
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
		bean.setOrder(0);
		return new CorsFilter(source);
	}

	@Bean
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {

		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);

		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		 Map<String, String>  result = filterChain();
		if(result!=null && result.size()>0) {
			filterChainDefinitionMap.putAll(result);
		}
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		Map<String, Filter> filter = new HashMap<String, Filter>();
		filter.put("ClientAuthc", new ShiroClientAuthenticatingFilter());
		filter.put("ClientLogout", new SystemLogoutFilter());
		filter.put("ClientRoles", new RolesAuthorizationFilter());
		shiroFilterFactoryBean.setFilters(filter);
		return shiroFilterFactoryBean;

	}

	/**
	 * 获得头
	 * 
	 * @return
	 */
	@Bean
	public RedissonWebSessionManager redissonWebSessionManager(@Autowired RedissonSessionDao redissonSessionDao) {

		RedissonWebSessionManager iDefaultWebSessionManager = new RedissonWebSessionManager() {

			private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";

			@Override
			protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
				HttpServletRequest servletRequest = (HttpServletRequest) request;
				String auth = servletRequest.getHeader(header_key_auth);
				if (StringUtils.isEmpty(auth)) {
					auth = servletRequest.getParameter(ShiroClientAuthenticatingFilter.key_session_id);
				}
				// 如果请求头中有 Authorization 则其值为sessionId
				if (!StringUtils.isEmpty(auth)) {
					request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
					request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, auth);
					request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
					return auth;
				} else {
					// 否则按默认规则从cookie取sessionId
					return super.getSessionId(request, response);
				}
			}
		};
		iDefaultWebSessionManager.setSessionDAO(redissonSessionDao);
		iDefaultWebSessionManager.setGlobalSessionTimeout(ShiroUnits.session_timeout);
		return iDefaultWebSessionManager;
	}

	@Bean
	public RedissonShiroCacheManager redissonShiroCacheManager(@Autowired @Qualifier("ShiroRedissonClient") RedissonClient redissonClient) {
		RedissonShiroCacheManager redisCacheManager = new RedissonShiroCacheManager();
		redisCacheManager.setRedisson(redissonClient);
		return redisCacheManager;
	}

	@Bean
	public org.apache.shiro.mgt.SecurityManager securityManager(@Autowired ShiroClientAuthorizingRealm managerPlatformAuthorizingRealm,
			@Autowired RedissonWebSessionManager redissonWebSessionManager,
			@Autowired RedissonShiroCacheManager redissonShiroCacheManager) {
		DefaultWebSecurityManager defaultSecurityManager = new DefaultWebSecurityManager();
		defaultSecurityManager.setRealm(managerPlatformAuthorizingRealm);
		defaultSecurityManager.setSessionManager(redissonWebSessionManager);

		defaultSecurityManager.setCacheManager(redissonShiroCacheManager);
		return defaultSecurityManager;
	}

	@Bean
	public AuthenticationInfoCache authenticationInfoCache(@Autowired @Qualifier("ShiroRedissonClient") RedissonClient redissonClient) {
		AuthenticationInfoCache iAuthenticationInfoCache = new AuthenticationInfoCache(redissonClient);
		return iAuthenticationInfoCache;
	}

	@Bean
	public AuthorizationInfoCache authorizationInfoCache(@Autowired @Qualifier("ShiroRedissonClient") RedissonClient redissonClient) {
		AuthorizationInfoCache iAuthenticationInfoCache = new AuthorizationInfoCache(redissonClient);
		return iAuthenticationInfoCache;
	}

	@Bean
	public ShiroClientAuthorizingRealm managerPlatformAuthorizingRealm(CredentialsMatcher credentialsMatcher,
			AuthenticationInfoCache authenticationInfoCache, AuthorizationInfoCache authorizationInfoCache ,@Autowired IShrioClientService shrioClientService) {
		ShiroClientAuthorizingRealm iCrawlerClientAuthorizingRealm = new ShiroClientAuthorizingRealm(credentialsMatcher,shrioClientService);
		iCrawlerClientAuthorizingRealm.setCredentialsMatcher(createCrawlerSimpleCredentialsMatcher());
		iCrawlerClientAuthorizingRealm.setAuthenticationCache(authenticationInfoCache);
		iCrawlerClientAuthorizingRealm.setAuthorizationCache(authorizationInfoCache);
		return iCrawlerClientAuthorizingRealm;
	}

	@Bean
	public ShiroClientCredentialsMatcher createCrawlerSimpleCredentialsMatcher() {
		ShiroClientCredentialsMatcher iCrawlerSimpleCredentialsMatcher = new ShiroClientCredentialsMatcher();
		return iCrawlerSimpleCredentialsMatcher;
	}

	// @Bean
	// public HashedCredentialsMatcher hashedCredentialsMatcher() {
	// HashedCredentialsMatcher hashedCredentialsMatcher = new
	// HashedCredentialsMatcher();
	// // 散列算法:这里使用MD5算法;
	// hashedCredentialsMatcher.setHashAlgorithmName("md5");
	// // 散列的次数，比如散列两次，相当于 md5(md5(""));
	// hashedCredentialsMatcher.setHashIterations(2);
	// return hashedCredentialsMatcher;
	// }

	@Bean
	public RedissonSessionDao redissonSessionDao(@Autowired @Qualifier("ShiroRedissonClient") RedissonClient redissonClient) {
		RedissonSessionDao redisSessionDAO = new RedissonSessionDao();
		redisSessionDAO.setRedisson(redissonClient);
		redisSessionDAO.setSessionIdGenerator(sessionIdGenerator());
		return redisSessionDAO;
	}

	@Bean
	public JavaUuidSessionIdGenerator sessionIdGenerator() {
		return new JavaUuidSessionIdGenerator();
	}

}
