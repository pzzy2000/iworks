package cn.oxo.iworks.web.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.oxo.iworks.databases.SysOptError;
import cn.oxo.iworks.web.LoginSessionBean;
import cn.oxo.iworks.web.UserType;
import cn.oxo.iworks.web.controller.bind.annotation.FormModel;

/*
 * /sys/login
 */
public abstract class SysUserLoginController extends ABSpringShiroBootController {

      private Logger logger = LogManager.getLogger(SysUserLoginController.class);

      protected abstract LoginSessionBean updateTokenByAccess(LoginInfoBean loginInfo, String token);

      @RequestMapping(value = "/login", method = { RequestMethod.POST, RequestMethod.GET })
      // @ApiOperation(value = "登录")
      public ResponseEntity<?> login(@FormModel(parameterName = "bean") LoginInfoBean bean) throws Exception {

            String sessionId = (String) SecurityUtils.getSubject().getSession().getId();
            logger.info(" login .......  " + bean.toString() + "  " + sessionId);
            switch (bean.getLogintype()) {
                  case manager: {
                        LoginSessionBean loginSessionBean = updateTokenByAccess(bean, sessionId);
                        loginSessionBean.setUserType(UserType.manager);

                        putSession(loginSessionBean);
                        return ResponseEntityUnits.createSuccessResponseEntity(loginSessionBean);
                  }

                  case client: {
                        LoginSessionBean loginSessionBean = updateTokenByAccess(bean, sessionId);
                        loginSessionBean.setUserType(UserType.client);
                        putSession(loginSessionBean);
                        return ResponseEntityUnits.createSuccessResponseEntity(loginSessionBean);

                  }

                  default:
                        return ResponseEntityUnits.createErrorResponseEntity(SysOptError.SysError.getCode(), SysOptError.SysError.getName());
            }

      }

      @RequestMapping(value = "/loginout", method = { RequestMethod.POST })
      public ResponseEntity<?> loginout(@FormModel(parameterName = "bean") LoginInfoBean loginBean) throws Exception {
            System.out.println("用户退出");
            SecurityUtils.getSubject().logout();

            return ResponseEntityUnits.createSuccessResponseEntity("退出成功");
      }

}
