package cn.oxo.iworks.web.shiro.simple;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;

import com.alibaba.fastjson.JSON;

import cn.oxo.iworks.web.UserType;
import cn.oxo.iworks.web.controller.ErrorResult;
import cn.oxo.iworks.web.controller.RequestResult;
import cn.oxo.iworks.web.shiro.ShiroClientAuthenticationException;
import cn.oxo.iworks.web.shiro.ShiroClientMsg;

public class ShiroAdminAuthenticatingFilter extends AuthenticatingFilter {
    private Logger logger = LogManager.getLogger(ShiroAdminAuthenticatingFilter.class);
    public static String key_user_access = "bean.access";
    private String key_user_password = "bean.password";
    // private String key_user_logintype = "bean.logintype";
    public static String key_session_id = "auth";
    private String url_login = "/admin/business/admin/login";

    protected AuthenticationToken createToken(ServletRequest request, ServletResponse arg1) throws Exception {

        HttpServletRequest httpRequest = (HttpServletRequest)request;

        String access = httpRequest.getParameter(key_user_access);

        String password = httpRequest.getParameter(this.key_user_password);

        // String logintype = httpRequest.getParameter(this.key_user_logintype);

        logger.info("admin login access : " + access + " code : " + password);

        ShiroClientUserPasswordToken crawlerClientUserPasswordToken = new ShiroClientUserPasswordToken(access, password);

        crawlerClientUserPasswordToken.setRememberMe(true);

        crawlerClientUserPasswordToken.setUserType(UserType.manager);

        return crawlerClientUserPasswordToken;
    }

    private boolean isLocalLoginPath(ServletRequest request, ServletResponse response) {

        boolean isisLoginRequest = pathsMatch(this.url_login, request);
        return isisLoginRequest;
    }

    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        ShiroClientUserPasswordToken crawlerClientUserPasswordToken = (ShiroClientUserPasswordToken)token;

        HttpServletRequest httpRequest = (HttpServletRequest)request;

        httpRequest.setAttribute(key_user_access, crawlerClientUserPasswordToken.getUsername());

        return true;
    }

    protected boolean onLoginFailure(AuthenticationToken token, Exception e, ServletRequest request, ServletResponse response) {
        this.logger.error(e.getMessage(), e);
        try {
            if ((e instanceof ShiroClientAuthenticationException)) {
                ShiroClientAuthenticationException iSystemOptServiceException = (ShiroClientAuthenticationException)e;
                toErrorPage(request, response, iSystemOptServiceException);
            } else {
                toErrorPage(request, response, ShiroClientMsg.ClientLoginFail);
            }
        } catch (Exception e1) {
            this.logger.error(e1.getMessage(), e1);
        }
        return true;
    }

    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        AuthenticationToken token = createToken(request, response);
        if (token == null) {
            String msg =
                "createToken method implementation returned null. A valid non-null AuthenticationToken must be created in order to execute a login attempt.";

            throw new IllegalStateException(msg);
        }
        try {
            Subject subject = getSubject(request, response);
            subject.logout();
            subject.login(token);
            return onLoginSuccess(token, subject, request, response);
        } catch (Exception e) {
            onLoginFailure(token, e, request, response);
        }
        return false;
    }

    // private Map<String, String> errorParams(ShiroClientMsg crawlerClientMsg) {
    // Map<String, String> params = new HashMap();
    // params.put("errorCode", crawlerClientMsg.getCode().toString());
    // params.put("errorMsg", crawlerClientMsg.getMsg());
    // return params;
    // }

    protected boolean toErrorPage(ServletRequest request, ServletResponse response, ShiroClientAuthenticationException e) throws Exception {
        RequestResult<ErrorResult> iRequestResult = new RequestResult<ErrorResult>();

        iRequestResult.setSuccess(true);

        iRequestResult.setMsg("");

        iRequestResult.setResult(new ErrorResult(e.getCode().intValue(), e.getOpenId()));

        HttpServletResponse iresponse = (HttpServletResponse)response;

        iresponse.setHeader("Content-type", "text/json;charset=UTF-8");

        iresponse.getWriter().write(JSON.toJSONString(iRequestResult));

        iresponse.getWriter().flush();

        return false;
    }

    protected boolean toErrorPage(ServletRequest request, ServletResponse response, ShiroClientMsg crawlerClientMsg) throws Exception {
        RequestResult<ErrorResult> iRequestResult = new RequestResult<ErrorResult>();

        iRequestResult.setSuccess(true);

        iRequestResult.setMsg("");

        iRequestResult.setResult(new ErrorResult(crawlerClientMsg.getCode().intValue(), crawlerClientMsg.getMsg()));

        HttpServletResponse iresponse = (HttpServletResponse)response;

        iresponse.setHeader("Content-type", "text/json;charset=UTF-8");

        iresponse.getWriter().write(JSON.toJSONString(iRequestResult));

        iresponse.getWriter().flush();

        return false;
    }

    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLocalLoginPath(request, response)) {
            return false;
        }
        Subject subject = getSubject(request, response);
        if (subject.isAuthenticated()) {
            return true;
        }
        return false;
    }

    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLocalLoginPath(request, response)) {
            String access = request.getParameter(key_user_access);

            String password = request.getParameter(this.key_user_password);

            // String logintype = request.getParameter(this.key_user_logintype);

            // this.logger.info("access " + access + " password " + password );
            if ((StringUtils.isEmpty(access)) || (StringUtils.isEmpty(password))) {
                return toErrorPage(request, response, ShiroClientMsg.ClientParamsError);
            }
            return executeLogin(request, response);
        }
        Subject subject = getSubject(request, response);
        if (subject.isAuthenticated()) {
            return true;
        }
        saveRequestAndRedirectToLogin(request, response);
        return false;
    }

    protected void saveRequestAndRedirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        RequestResult<ErrorResult> iRequestResult = new RequestResult<ErrorResult>();

        iRequestResult.setSuccess(false);

        iRequestResult.setMsg("请求成功");

        iRequestResult.setResult(new ErrorResult(ShiroClientMsg.ClientNeedLogin.getCode().intValue(), ShiroClientMsg.ClientNeedLogin.getMsg()));

        HttpServletResponse iresponse = (HttpServletResponse)response;

        iresponse.setHeader("Content-type", "text/json;charset=UTF-8");

        iresponse.getWriter().write(JSON.toJSONString(iRequestResult));

        iresponse.getWriter().flush();
    }

}
