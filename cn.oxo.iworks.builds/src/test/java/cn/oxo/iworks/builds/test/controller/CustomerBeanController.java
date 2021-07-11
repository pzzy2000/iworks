 package cn.oxo.iworks.builds.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.zy.dev.tools.units.CommSearchBean;
import cn.zy.dev.tools.units.SelectPage;
import cn.zy.dev.tools.units.SQLUilts.OptType;
import cn.zy.dev.tools.web.spring.bind.annotation.FormModel;
import cn.zy.dev.tools.web.spring.controller.ResponseEntityUnits;
import cn.zy.dev.tools.web.spring.shiro.client.ABShirOAuth2ClientHtmlController;

import java.util.ArrayList;
import java.util.List;



import cn.oxo.iworks.builds.test.bean.CustomerBean;
import cn.oxo.iworks.builds.test.pojo.bean.CustomerBeanSearchBean;
import cn.oxo.iworks.builds.test.controller.aop.IAopCustomerBeanService;
@Controller
@RequestMapping("/business/customer")
public class CustomerBeanController extends ABShirOAuth2ClientHtmlController {

 @Autowired
 @Qualifier(IAopCustomerBeanService.name)
 private IAopCustomerBeanService service ;
	
	 
	@RequestMapping(value = "/list", method = { RequestMethod.POST })
 public ResponseEntity<?> list(OptType optType, 
 @FormModel(parameterName = "bean") CustomerBeanSearchBean searchBean,
			@FormModel(parameterName = "commSearchBean") CommSearchBean commSearchBean) throws Exception {
 
 SelectPage<CustomerBean> selectPage = service.search(optType, searchBean, commSearchBean, getLoginUserId()) ; 
 return ResponseEntityUnits.createSelectPageResponseEntity(selectPage);
 
 }
 
 @RequestMapping(value = "/save", method = { RequestMethod.POST })
	public ResponseEntity<?> save(OptType optType, @FormModel(parameterName = "bean") CustomerBean bean) throws Exception {

		CustomerBean iCustomerBean = service.saveUpdate(optType, bean, getLoginUserId());
		return ResponseEntityUnits.createSuccessResponseEntity(iCustomerBean);

	}
	
	@RequestMapping(value = "/get", method = { RequestMethod.POST })
	public ResponseEntity<?> get(OptType optType, @FormModel(parameterName = "bean") CustomerBeanSearchBean bean) throws Exception {

		CustomerBean iCustomerBean = service.get(bean.getId());
		return ResponseEntityUnits.createSuccessResponseEntity(iCustomerBean);

	}

	@RequestMapping(value = "/delete", method = { RequestMethod.POST })
	public ResponseEntity<?> delete(OptType optType, @FormModel(parameterName = "bean") CustomerBeanSearchBean bean) throws Exception {

		
		List<CustomerBean> iCustomerBeans =service.removes(optType, bean, getLoginUserId());

	
		return ResponseEntityUnits.createSuccessResponseEntity(iCustomerBeans);

	}

}
