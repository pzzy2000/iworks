package cn.oxo.iworks.builds.test.service.imples.base;

import java.util.ArrayList;
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

import cn.oxo.iworks.builds.test.service.units.CustomerSearchUnits;
import cn.oxo.iworks.builds.test.service.units.CustomerSaveUpdateRemoveUnits;

//@Component(ICustomerService.name)
public abstract class CustomerBaseServiceImple {

	@Autowired
	@Qualifier("CustomerSearchUnits")
	private CustomerSearchUnits iCustomerSearchUnits;

	@Autowired
	@Qualifier("CustomerSaveUpdateRemoveUnits")
	private CustomerSaveUpdateRemoveUnits iCustomerSaveUpdateRemoveUnits;

	public CustomerBean saveUpdate(OptType optType, CustomerBean optCustomerBean, Long optUserId)
			throws SystemOptServiceException {
		return iCustomerSaveUpdateRemoveUnits.saveUpdate(optType, optCustomerBean);
	}

	public SelectPage<CustomerBean> search(OptType optType, CustomerSearchBean searchBean,
			CommSearchBean commSearchBean, Long optUserId) throws SystemOptServiceException {
		return iCustomerSearchUnits.search(optType, searchBean, commSearchBean);
	}

	public List<CustomerBean> searchList(OptType optType, CustomerSearchBean searchBean, CommSearchBean commSearchBean)
			throws SystemOptServiceException {

		return iCustomerSearchUnits.list(optType, searchBean, commSearchBean);

	}

	public CustomerBean remove(OptType optType, CustomerSearchBean searchBean, Long optUserId)
			throws SystemOptServiceException {
		return iCustomerSaveUpdateRemoveUnits.remove(optType, searchBean);
	}

	public List<CustomerBean> removes(OptType optType, CustomerSearchBean searchBean, Long optUserId)
			throws SystemOptServiceException {

		return iCustomerSaveUpdateRemoveUnits.removes(optType, searchBean);

	}

	public CustomerBean get(Long id) throws SystemOptServiceException {

		return iCustomerSaveUpdateRemoveUnits.get(id);
	}

	public CustomerBean searchSingle(OptType optType, CustomerSearchBean searchBean) throws SystemOptServiceException {

		return iCustomerSearchUnits.searchSingle(optType, searchBean);
	}

}
