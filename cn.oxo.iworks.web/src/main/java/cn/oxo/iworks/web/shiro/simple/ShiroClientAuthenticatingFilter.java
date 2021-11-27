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
import cn.oxo.iworks.web.shiro.LoginType;
import cn.oxo.iworks.web.shiro.ShiroClientAuthenticationException;
import cn.oxo.iworks.web.shiro.ShiroClientMsg;


/**
 * avatarUrl: https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqaMgaRyPApTUZuvbSW0hFbBE00aJW5ut4EyibMick3MMhyAibEpZxGs7UkRMkWooCkmJ4SN2iaTnFrag/132 access: MM password: 0217muFa1jgn6C0HDxIa1SAyWV07muFJ logintype: Wx
 * 
 * @author Administrator
 * @date 2021/11/13
 */
public class ShiroClientAuthenticatingFilter extends AuthenticatingFilter {
    private Logger logger = LogManager.getLogger(ShiroClientAuthenticatingFilter.class);

    private String key_user_logintype = "loginType";

    private String key_user_sysType = "sysType";

    public static String key_user_username = "name";

    public static String key_user_access = "access";
    private String key_user_password = "password";

    private String key_wx_user_code = "code";

    private String key_wx_user_avatarUrl = "avatarUrl";

    public static String key_session_id = "auth";

    private String url_login = "/client/imbuy/login";

    protected AuthenticationToken createToken (ServletRequest request, ServletResponse arg1) throws Exception {

        HttpServletRequest httpRequest = (HttpServletRequest)request;

        LoginType logintype = LoginType.valueOf(httpRequest.getParameter(this.key_user_logintype));

//        SysType buySysType = SysType.valueOf(request.getParameter(this.key_user_sysType));

        switch (logintype) {
            case Access: {
                String access = httpRequest.getParameter(key_user_access);

                String password = httpRequest.getParameter(this.key_user_password);

                ShiroClientUserPasswordToken crawlerClientUserPasswordToken = new ShiroClientUserPasswordToken(access, password);
                crawlerClientUserPasswordToken.setUserType(UserType.client);
                crawlerClientUserPasswordToken.setLoginType(LoginType.Access);
//                crawlerClientUserPasswordToken.setBuySysType(buySysType);
                return crawlerClientUserPasswordToken;
            }
            case Wx: {

                String avatarUrl = request.getParameter(key_wx_user_avatarUrl);

                String code = request.getParameter(key_wx_user_code);

                String name = request.getParameter(key_user_username);

                ShiroClientUserPasswordToken crawlerClientUserPasswordToken = new ShiroClientUserPasswordToken(name, code, avatarUrl);
                crawlerClientUserPasswordToken.setUserType(UserType.client);
                crawlerClientUserPasswordToken.setLoginType(LoginType.Wx);
//                crawlerClientUserPasswordToken.setBuySysType(buySysType);
                return crawlerClientUserPasswordToken;
            }

            default:
                throw new Exception("not support !");
        }

    }

    private boolean isLocalLoginPath (ServletRequest request, ServletResponse response) {
        boolean isisLoginRequest = pathsMatch(this.url_login, request);
        return isisLoginRequest;
    }

    protected boolean onLoginSuccess (AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        ShiroClientUserPasswordToken crawlerClientUserPasswordToken = (ShiroClientUserPasswordToken)token;
        request.setAttribute("openid", crawlerClientUserPasswordToken.getOpenId());
        return true;
    }

    protected boolean onLoginFailure (AuthenticationToken token, Exception e, ServletRequest request, ServletResponse response) {
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

    protected boolean executeLogin (ServletRequest request, ServletResponse response) throws Exception {
        AuthenticationToken token = createToken(request, response);
        if (token == null) {
            String msg = "createToken method implementation returned null. A valid non-null AuthenticationToken must be created in order to execute a login attempt.";

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

    protected boolean toErrorPage (ServletRequest request, ServletResponse response, ShiroClientAuthenticationException e) throws Exception {
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

    protected boolean toErrorPage (ServletRequest request, ServletResponse response, ShiroClientMsg crawlerClientMsg) throws Exception {
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

    protected boolean isAccessAllowed (ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLocalLoginPath(request, response)) {
            return false;
        }
        Subject subject = getSubject(request, response);
        if (subject.isAuthenticated()) {
            return true;
        } else {
            return false;
        }

    }

    protected boolean onAccessDenied (ServletRequest request, ServletResponse response) throws Exception {

        if (isLocalLoginPath(request, response)) {
            try {

                LoginType logintype = LoginType.valueOf(request.getParameter(this.key_user_logintype));

//                SysType buySysType = SysType.valueOf(request.getParameter(this.key_user_sysType));

//                if (buySysType == null) {
//                    return toErrorPage(request, response, ShiroClientMsg.ClientParamsError);
//                }

                switch (logintype) {
                    case Access: {
                        String access = request.getParameter(key_user_access);

                        String password = request.getParameter(this.key_user_password);

                        if ((StringUtils.isEmpty(access)) || (StringUtils.isEmpty(password)) || access.length() > 10 || password.length() > 10) {
                            return toErrorPage(request, response, ShiroClientMsg.ClientParamsError);
                        } else {
                            return executeLogin(request, response);
                        }

                    }

                    case Wx: {
                        String avatarUrl = request.getParameter(key_wx_user_avatarUrl);

                        String code = request.getParameter(key_wx_user_code);

                        String name = request.getParameter(key_user_username);

                        if ((StringUtils.isEmpty(code)) || (StringUtils.isEmpty(name)) || code.length() > 100 || name.length() > 100) {
                            return toErrorPage(request, response, ShiroClientMsg.ClientParamsError);
                        } else {
                            if (avatarUrl != null && avatarUrl.length() > 255) {
                                return toErrorPage(request, response, ShiroClientMsg.ClientParamsError);
                            } else {
                                return executeLogin(request, response);
                            }

                        }

                    }

                    default:
                        return toErrorPage(request, response, ShiroClientMsg.ClientParamsError);
                }

            } catch (Exception e) {
                return toErrorPage(request, response, ShiroClientMsg.ClientParamsError);
            }

        }
        Subject subject = getSubject(request, response);
        if (subject.isAuthenticated()) {
            return true;
        }
        saveRequestAndRedirectToLogin(request, response);
        return false;
    }

    protected void saveRequestAndRedirectToLogin (ServletRequest request, ServletResponse response) throws IOException {
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
