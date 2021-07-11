package cn.oxo.iworks.builds.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.oxo.iworks.web.controller.ABSpringShiroBootController;
import cn.oxo.iworks.web.controller.ResponseEntityUnits;
import cn.oxo.iworks.web.controller.bind.annotation.FormModel;

import cn.oxo.iworks.builds.test.bean.CustomerBean;
import cn.oxo.iworks.builds.test.bean.search.CustomerSearchBean;
import cn.oxo.iworks.builds.test.controller.aop.IAopCustomerExService;

@Controller
@RequestMapping("/business/customer")
public class CustomerController extends ABSpringShiroBootController { // ABShirOAuth2ClientHtmlController

	@Autowired
	@Qualifier(IAopCustomerExService.name)
	private IAopCustomerExService service;

	@RequestMapping(value = "/list", method = { RequestMethod.POST })
	public ResponseEntity<?> list(OptType optType, @FormModel(parameterName = "bean") CustomerSearchBean searchBean,
			@FormModel(parameterName = "commSearchBean") CommSearchBean commSearchBean) throws Exception {

		SelectPage<CustomerBean> selectPage = service.search(optType, searchBean, commSearchBean, getLoginUserId());
		return ResponseEntityUnits.createSelectPageResponseEntity(selectPage);

	}

	// @RequestMapping(value = "/save", method = { RequestMethod.POST })
	// public ResponseEntity<?> save(OptType optType, @FormModel(parameterName =
	// "bean") CustomerBean bean) throws Exception {
	// CustomerBean iCustomerBean = service.saveUpdate(optType, bean,
	// getLoginUserId());
	// return ResponseEntityUnits.createSuccessResponseEntity(iCustomerBean);
	// }

	@RequestMapping(value = "/get", method = { RequestMethod.POST })
	public ResponseEntity<?> get(OptType optType, @FormModel(parameterName = "bean") CustomerSearchBean bean)
			throws Exception {

		CustomerBean iCustomerBean = service.get(bean.getId());
		return ResponseEntityUnits.createSuccessResponseEntity(iCustomerBean);

	}

	@RequestMapping(value = "/delete", method = { RequestMethod.POST })
	public ResponseEntity<?> delete(OptType optType, @FormModel(parameterName = "bean") CustomerSearchBean bean)
			throws Exception {

		List<CustomerBean> iCustomerBeans = service.removes(optType, bean, getLoginUserId());

		return ResponseEntityUnits.createSuccessResponseEntity(iCustomerBeans);

	}

}
