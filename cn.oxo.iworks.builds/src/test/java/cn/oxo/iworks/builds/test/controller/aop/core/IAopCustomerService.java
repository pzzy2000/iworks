package cn.oxo.iworks.builds.test.controller.aop.core;


 import java.util.List;

import cn.oxo.iworks.databases.CommSearchBean;
import cn.oxo.iworks.databases.SQLUilts.OptType;
import cn.oxo.iworks.databases.SelectPage;
import cn.oxo.iworks.databases.SystemOptServiceException;
 
 
import cn.oxo.iworks.builds.test.bean.CustomerBean;
import cn.oxo.iworks.builds.test.bean.search.CustomerSearchBean;



public interface IAopCustomerService {
 
 public String name="IAopCustomerService";
 
 /**
 * 增加或更新
 */
 // public CustomerBean saveUpdate(OptType optType , CustomerBean iCustomerBean, Long optUserId)throws SystemOptServiceException;
 
 public SelectPage<CustomerBean > search(OptType optType , CustomerSearchBean searchBean,CommSearchBean commSearchBean , Long optUserId )throws SystemOptServiceException;
 
 public List<CustomerBean > searchList(OptType optType , CustomerSearchBean searchBean,CommSearchBean commSearchBean )throws SystemOptServiceException;
 
 public CustomerBean searchSingle(OptType optType, CustomerSearchBean searchBean)throws SystemOptServiceException;
 
 public CustomerBean remove(OptType optType , CustomerSearchBean searchBean,Long optUserId)throws SystemOptServiceException;
 
 public List<CustomerBean > removes(OptType optType , CustomerSearchBean searchBean,Long optUserId)throws SystemOptServiceException;
 
 public CustomerBean get(Long id)throws SystemOptServiceException;
 
 
 
 

}
