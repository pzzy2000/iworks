package cn.oxo.iworks.builds.test.controller.aop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import cn.oxo.iworks.databases.CommSearchBean;
import cn.oxo.iworks.databases.SQLUilts.OptType;
import cn.oxo.iworks.databases.SelectPage;
import cn.oxo.iworks.databases.SystemOptServiceException;


import cn.oxo.iworks.builds.test.bean.CustomerBean;
import cn.oxo.iworks.builds.test.bean.search.CustomerSearchBean;

import cn.oxo.iworks.builds.test.controller.aop.core.imples.AopCustomerExBaseService;
import cn.oxo.iworks.builds.test.controller.aop.core.IAopCustomerService;


@Component(IAopCustomerService.name)
public class AopCustomerExService extends AopCustomerExBaseService implements IAopCustomerExService {

 
 

}
