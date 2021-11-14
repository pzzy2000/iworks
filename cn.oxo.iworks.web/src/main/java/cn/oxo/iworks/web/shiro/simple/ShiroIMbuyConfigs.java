package cn.oxo.iworks.web.shiro.simple;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.Filter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.redisson.api.RedissonClient;
import org.redisson.spring.cache.CacheConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.github.streamone.shiro.cache.RedissonShiroCacheManager;
import com.github.streamone.shiro.session.RedissonSessionDao;
import com.github.streamone.shiro.session.RedissonWebSessionManager;

import cn.oxo.iworks.web.shiro.AuthenticationInfoCache;
import cn.oxo.iworks.web.shiro.AuthorizationInfoCache;
import cn.oxo.iworks.web.shiro.ShiroClientAuthenticatingFilter;
import cn.oxo.iworks.web.shiro.ShiroClientShiroConfig;
import cn.oxo.iworks.web.shiro.ShiroUnits;

@Configuration
public class ShiroIMbuyConfigs {

    protected Map<String, String> filterChain () {
        Map<String, String> filterChains = new LinkedHashMap<String, String>();

        filterChains.put("/ecmi/client/reg", "NoAuthc");
        filterChains.put("/ecmi/client/config/base", "NoAuthc");

        filterChains.put("/ecmi/client/login/out", "ClientExitAuthc");

        // filterChains.put("/client/coupon/goods/order/**", "ClientAuthc");
        // filterChains.put("/client/coupon/goods/**", "NoAuthc");

        // filterChains.put("/client/business/portal/beaner/list", "NoAuthc");
        // filterChains.put("/client/business/portal/category/list", "NoAuthc");
        // filterChains.put("/client/business/coupon/goods/list", "NoAuthc");
        // filterChains.put("/client/business/coupon/goods/detail/get", "NoAuthc");
        //
        // filterChains.put("/client/business/customer/login/out", "ClientExitAuthc");
        // filterChains.put("/admin/business/admin/loginout", "AdminExitAuthc");
        //
        filterChains.put("/client/imbuy/**", "ClientAuthc");
        filterChains.put("/admin/**", "AdminAuthc");
        // filterChains.put("/**", "ClientAuthc,ClientRoles[manager]");
        return filterChains;
    }

    private static Logger logger = LogManager.getLogger(ShiroClientShiroConfig.class);

    public static String header_key_auth = "auth";

    @Autowired
    private Environment env;

    /*
     * 
     */

    // /**
    // * 允许跨域调用的过滤器
    // */
    // @SuppressWarnings({"rawtypes", "unchecked"})
    // @Bean
    // public CorsFilter corsFilter() {
    //
    // UrlBasedCorsConfigurationSource source = new
    // UrlBasedCorsConfigurationSource();
    // CorsConfiguration config = new CorsConfiguration();
    // config.addAllowedOrigin("*");
    // config.setAllowCredentials(true);
    // config.addAllowedHeader("*");
    // config.addAllowedMethod("*");
    // source.registerCorsConfiguration("/**", config);
    // FilterRegistrationBean bean = new FilterRegistrationBean(new
    // CorsFilter(source));
    // bean.setOrder(0);
    // return new CorsFilter(source);
    // }

    @Bean
    public ShiroFilterFactoryBean shiroFilter (SecurityManager securityManager) {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        Map<String, String> result = filterChain();
        if (result != null && result.size() > 0) {
            // filterChainDefinitionMap.putAll(result);
            shiroFilterFactoryBean.setFilterChainDefinitionMap(result);
        }

        Map<String, Filter> filter = new HashMap<String, Filter>();
        filter.put("ClientAuthc", new ShiroIMbuyClientAuthenticatingFilter());
        filter.put("ClientExitAuthc", new ShiroWxClientLoginOutAuthenticatingFilter());
        filter.put("AdminAuthc", new ShiroIMbuyAdminAuthenticatingFilter());
        filter.put("NoAuthc", new NoAnonymousFilter());
        filter.put("AdminExitAuthc", new ShiroIMbuyAdminLoginOutAuthenticatingFilter());
        // filter.put("ClientRoles", new RolesAuthorizationFilter());
        shiroFilterFactoryBean.setFilters(filter);
        return shiroFilterFactoryBean;

    }

    @Bean
    public CookieRememberMeManager cookieRememberMeManager () {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        simpleCookie.setMaxAge(259200000);
        cookieRememberMeManager.setCookie(simpleCookie);
        cookieRememberMeManager.setCipherKey(Base64.decode("6ZmI6I2j5Y+R5aSn5ZOlAA=="));
        return cookieRememberMeManager;
    }

    @Bean
    public RedissonWebSessionManager redissonWebSessionManager (@Autowired RedissonSessionDao redissonSessionDao) {

        RedissonWebSessionManager iDefaultWebSessionManager = new RedissonWebSessionManager() {

            private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";

            @Override
            protected Serializable getSessionId (ServletRequest request, ServletResponse response) {
                HttpServletRequest servletRequest = (HttpServletRequest)request;
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
        // iDefaultWebSessionManager.setGlobalSessionTimeout(ShiroUnits.session_timeout);
        iDefaultWebSessionManager.setGlobalSessionTimeout(60 * 60 * 24 * 360);
        return iDefaultWebSessionManager;
    }

    @Bean
    public RedissonShiroCacheManager redissonShiroCacheManager (@Autowired @Qualifier("ShiroRedissonClient") RedissonClient redissonClient) {
        RedissonShiroCacheManager redisCacheManager = new RedissonShiroCacheManager();
        redisCacheManager.setRedisson(redissonClient);
        // Map<String, CacheConfig> configMap = new ConcurrentHashMap<>();
        // configMap.put("shiro-authenticationCache", new CacheConfig(60*60*24*180, 60*60*24*(180+30)));
        // configMap.put("shiro-authorizationCache", new CacheConfig(60*60*24*180, 60*60*24*(180+30)));
        // redisCacheManager.setConfig(configMap);
        return redisCacheManager;
    }

    @Bean
    public org.apache.shiro.mgt.SecurityManager securityManager (@Autowired ShiroWxClientAuthorizingRealm managerPlatformAuthorizingRealm, @Autowired RedissonWebSessionManager redissonWebSessionManager,
        @Autowired RedissonShiroCacheManager redissonShiroCacheManager) {
        DefaultWebSecurityManager defaultSecurityManager = new DefaultWebSecurityManager();
        defaultSecurityManager.setRealm(managerPlatformAuthorizingRealm);
        defaultSecurityManager.setSessionManager(redissonWebSessionManager);
        defaultSecurityManager.setRememberMeManager(cookieRememberMeManager());
        defaultSecurityManager.setCacheManager(redissonShiroCacheManager);
        return defaultSecurityManager;
    }

    @Bean
    public AuthenticationInfoCache authenticationInfoCache (@Autowired @Qualifier("ShiroRedissonClient") RedissonClient redissonClient) {
        AuthenticationInfoCache iAuthenticationInfoCache = new AuthenticationInfoCache(redissonClient);
        return iAuthenticationInfoCache;
    }

    @Bean
    public AuthorizationInfoCache authorizationInfoCache (@Autowired @Qualifier("ShiroRedissonClient") RedissonClient redissonClient) {
        AuthorizationInfoCache iAuthenticationInfoCache = new AuthorizationInfoCache(redissonClient);
        return iAuthenticationInfoCache;
    }

    @Bean
    public ShiroWxClientAuthorizingRealm managerPlatformAuthorizingRealm (CredentialsMatcher credentialsMatcher, AuthenticationInfoCache authenticationInfoCache, AuthorizationInfoCache authorizationInfoCache,
        @Autowired IShiroExClientService shrioClientService) {
        ShiroWxClientAuthorizingRealm iCrawlerClientAuthorizingRealm = new ShiroWxClientAuthorizingRealm(credentialsMatcher, shrioClientService);
        iCrawlerClientAuthorizingRealm.setCredentialsMatcher(createCrawlerSimpleCredentialsMatcher());
        // iCrawlerClientAuthorizingRealm.setAuthenticationCache(authenticationInfoCache);
        // iCrawlerClientAuthorizingRealm.setAuthorizationCache(authorizationInfoCache);
        return iCrawlerClientAuthorizingRealm;
    }

    @Bean
    public ShiroWxSimpleCredentialsMatcher createCrawlerSimpleCredentialsMatcher () {
        ShiroWxSimpleCredentialsMatcher iCrawlerSimpleCredentialsMatcher = new ShiroWxSimpleCredentialsMatcher();
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
    public RedissonSessionDao redissonSessionDao (@Autowired @Qualifier("ShiroRedissonClient") RedissonClient redissonClient) {
        RedissonSessionDao redisSessionDAO = new RedissonSessionDao();

        redisSessionDAO.setRedisson(redissonClient);
        redisSessionDAO.setSessionIdGenerator(sessionIdGenerator());

        return redisSessionDAO;
    }

    @Bean
    public JavaUuidSessionIdGenerator sessionIdGenerator () {
        return new JavaUuidSessionIdGenerator();
    }

}
