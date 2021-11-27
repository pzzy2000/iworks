package cn.oxo.iworks.web.shiro.simple;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;

import com.alibaba.fastjson.JSON;

import cn.oxo.iworks.web.controller.ErrorResult;
import cn.oxo.iworks.web.controller.RequestResult;

public class ShiroClientLoginOutAuthenticatingFilter extends LogoutFilter {

    private String url_login = "/ecmi/client/login/out";

    private static RequestResult<ErrorResult> iRequestResult = new RequestResult<ErrorResult>();

    static {
        iRequestResult.setSuccess(true);

        iRequestResult.setMsg("请求成功");

        iRequestResult.setResult(new ErrorResult(0, "退出成功"));
    }

    private Logger logger = LogManager.getLogger(ShiroClientLoginOutAuthenticatingFilter.class);

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        ServletContext context = request.getServletContext();
        logger.info("client login out .............................");
        try {
            context.removeAttribute("error");

            if (subject.isAuthenticated())
                subject.logout();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        HttpServletResponse iresponse = (HttpServletResponse)response;

        iresponse.setHeader("Content-type", "text/json;charset=UTF-8");

        iresponse.getWriter().write(JSON.toJSONString(iRequestResult));

        iresponse.getWriter().flush();

        return false;
    }

}
