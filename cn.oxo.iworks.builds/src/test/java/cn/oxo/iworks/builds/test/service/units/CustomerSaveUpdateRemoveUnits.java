package cn.oxo.iworks.builds.test.service.units;

import java.util.List;

import org.springframework.stereotype.Component ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import cn.oxo.iworks.builds.test.bean.CustomerBean;
import cn.oxo.iworks.databases.ABBaseUnits;
import cn.oxo.iworks.databases.SQLUilts.OptType;
import cn.oxo.iworks.databases.SystemOptServiceException;
import cn.oxo.iworks.databases.SysOptError;
import cn.oxo.iworks.builds.test.service.mybatis.CustomerBaseMapper;
import cn.oxo.iworks.builds.test.bean.search.CustomerSearchBean;


@Component("CustomerSaveUpdateRemoveUnits")
public class CustomerSaveUpdateRemoveUnits extends ABBaseUnits {

@Autowired
private CustomerBaseMapper iCustomerBaseMapper;




 public CustomerBean saveUpdate(OptType optType, CustomerBean optCustomerBean) throws SystemOptServiceException {

 switch (optType) {
		
		 case init:
		 return init(optCustomerBean) ;
 case save:
 return save(optCustomerBean) ;
 
 case update:
 return update(optCustomerBean) ;
 

 default:
 throw new SystemOptServiceException(SysOptError.SysError.getCode(),SysOptError.SysError.getName()+"[操作类型错误]") ;
 }
 }
	
	 public CustomerBean init(CustomerBean optCustomerBean) throws SystemOptServiceException {
 throw new SystemOptServiceException(SysOptError.SysError.getCode(),SysOptError.SysError.getName()+"[操作没实现]") ;
 }

 public CustomerBean save(CustomerBean optCustomerBean) throws SystemOptServiceException {
 
 optCustomerBean.setId(createIdFactory.createDBId());

 int r = iCustomerBaseMapper.insertSelective(optCustomerBean);
 
 if(r==0)throw new SystemOptServiceException(SysOptError.SysError.getCode(),SysOptError.SysError.getName()+"[插入数据错误]");
 
 return optCustomerBean;
 }

 public CustomerBean update(CustomerBean optCustomerBean) throws SystemOptServiceException {

 int r = iCustomerBaseMapper.updateByPrimaryKeySelective(optCustomerBean);
 
 if(r==0)throw new SystemOptServiceException(SysOptError.SysError.getCode(),SysOptError.SysError.getName()+"[更新数据错误]");
 
 return get(optCustomerBean.getId());
 }
 
 public CustomerBean get(Long id) throws SystemOptServiceException {

 return iCustomerBaseMapper.selectByPrimaryKey(id);
 
 }
 
 public CustomerBean remove(OptType optType, CustomerSearchBean searchBean ) throws SystemOptServiceException {
 
 CustomerBean removeCustomerBean =get(searchBean.getId());
 
 throw new SystemOptServiceException(SysOptError.SysError.getCode(),SysOptError.SysError.getName()+"[操作没实现]");
 }
 
 public List<CustomerBean> removes(OptType optType, CustomerSearchBean searchBean ) throws SystemOptServiceException {
 
 
 throw new SystemOptServiceException(SysOptError.SysError.getCode(),SysOptError.SysError.getName()+"[操作没实现]");
 }

}
