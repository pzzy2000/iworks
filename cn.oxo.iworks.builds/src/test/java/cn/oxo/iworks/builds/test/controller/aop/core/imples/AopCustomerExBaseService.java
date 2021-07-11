package cn.oxo.iworks.builds.test.controller.aop.core.imples;

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

import cn.oxo.iworks.builds.test.controller.aop.core.IAopCustomerService;



//@Component(IAopCustomerService.name)
public abstract class AopCustomerExBaseService extends AopCustomerService {

 
 // @Override
 // public CustomerBean saveUpdate(OptType optType , CustomerBean iCustomerBean,Long optUserId )throws SystemOptServiceException{
	// throw new SystemOptServiceException(1001, "暂不支持"); 
	// }
 
 	 @Override 
 public SelectPage<CustomerBean > search(OptType optType , 
				 CustomerSearchBean searchBean,CommSearchBean commSearchBean ,Long optUserId )throws SystemOptServiceException{
	
	 throw new SystemOptServiceException(1001, "暂不支持");
					
	}
	@Override
	public List<CustomerBean > searchList(OptType optType , 
				 CustomerSearchBean searchBean,CommSearchBean commSearchBean )throws SystemOptServiceException{
 
			 throw new SystemOptServiceException(1001, "暂不支持");
 
 }
 @Override
 public CustomerBean remove(OptType optType , CustomerSearchBean searchBean,Long optUserId)throws SystemOptServiceException{
			
			 throw new SystemOptServiceException(1001, "暂不支持");
			
	}
	@Override
	 public List<CustomerBean > removes(OptType optType , CustomerSearchBean searchBean
			,Long optUserId
			)throws SystemOptServiceException{
			 throw new SystemOptServiceException(1001, "暂不支持");
			}
			
 
 @Override
 public CustomerBean get(Long id)throws SystemOptServiceException
		 {
		 throw new SystemOptServiceException(1001, "暂不支持");
		 
		 }
		 
		 @Override
	public CustomerBean searchSingle(OptType optType, CustomerSearchBean searchBean) throws SystemOptServiceException {
		// TODO Auto-generated method stub
		 throw new SystemOptServiceException(1001, "暂不支持");
	}

}
