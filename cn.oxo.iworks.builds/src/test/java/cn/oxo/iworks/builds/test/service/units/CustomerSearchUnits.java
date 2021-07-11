package cn.oxo.iworks.builds.test.service.units;

import java.util.List ;
import org.apache.commons.lang3.StringUtils;

import org.springframework.stereotype.Component ;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.oxo.iworks.builds.test.bean.CustomerBean;

import cn.oxo.iworks.builds.test.bean.search.CustomerSearchBean;
import cn.oxo.iworks.builds.test.service.mybatis.CustomerBaseMapper;
import cn.oxo.iworks.databases.ABBaseUnits;
import cn.oxo.iworks.databases.CommSearchBean;
import cn.oxo.iworks.databases.SQLUilts.OptType;
import cn.oxo.iworks.databases.SysOptError;
import cn.oxo.iworks.databases.SystemOptServiceException;
import cn.oxo.iworks.databases.SelectPage;

@Component("CustomerSearchUnits")
public class CustomerSearchUnits extends ABBaseUnits {

@Autowired
private CustomerBaseMapper iCustomerBaseMapper;

 @SuppressWarnings("deprecation")
	private Page<CustomerBean> createPage(OptType optType,
		 CustomerSearchBean searchBean,CommSearchBean commSearchBean){
		if(commSearchBean ==null){
		return null;
		}else{
		Page<CustomerBean> page = new Page<CustomerBean>(
					commSearchBean.getPageNum() == null ? 1 : commSearchBean.getPageNum(),
					commSearchBean.getPageSize() == null ? 10 : commSearchBean.getPageSize());
		if(StringUtils.isNotEmpty(commSearchBean.getDir())) {
			String sort =StringUtils.isNotEmpty(commSearchBean.getSort())?"desc":commSearchBean.getSort();
			switch (sort.toLowerCase()) {
			case "asc":
				page.setAsc(commSearchBean.getDir());
				break;
			case "desc":
				page.setDesc(commSearchBean.getDir());
				break;
			default:
				break;
			}
		}
		return page;
		}
	}

 public SelectPage<CustomerBean> search(OptType optType,
		 CustomerSearchBean searchBean,CommSearchBean commSearchBean) throws SystemOptServiceException {
 
 Page<CustomerBean> page =createPage(optType, searchBean, commSearchBean);
		 List<CustomerBean> result= list(page, searchBean, commSearchBean);
		 SelectPage<CustomerBean> selectPage = new SelectPage<CustomerBean>();
		if (page == null) {
			selectPage.setTotal((long) result.size());
			selectPage.setSize(result.size());
			selectPage.setCurrent(result.size());
			selectPage.setRecords(result);
		} else {
			selectPage.setTotal(page.getTotal());
			selectPage.setSize(page.getSize());
			selectPage.setCurrent(page.getCurrent());
			selectPage.setRecords(result);
		}
		
		return selectPage;

 }
	
	
	 public List<CustomerBean> list(OptType optType,
		 CustomerSearchBean searchBean,CommSearchBean commSearchBean) throws SystemOptServiceException {
 
 Page<CustomerBean> page =createPage(optType, searchBean, commSearchBean);
 if(page ==null){
 return iCustomerBaseMapper.searchCustomerSelective(searchBean,commSearchBean);
 }else{
 List<CustomerBean> result = list(page,searchBean, commSearchBean);

		return result;
 }
 

 }
	
 private List<CustomerBean> list(Page<CustomerBean> page ,CustomerSearchBean searchBean, CommSearchBean commSearchBean) throws SystemOptServiceException {

		return iCustomerBaseMapper.searchCustomerSelective(page,searchBean,commSearchBean);

	}
	
	
	

 public CustomerBean searchSingle(OptType optType, CustomerSearchBean searchBean) throws SystemOptServiceException {
 CommSearchBean commSearchBean = new CommSearchBean();
		commSearchBean.setPageNum(1);
		commSearchBean.setPageSize(1);
		Page<CustomerBean> page =createPage(optType, searchBean, commSearchBean);
		List<CustomerBean> result = list(page, searchBean,commSearchBean);
		if(result.size()>1)throw new SystemOptServiceException(SysOptError.SysError.getCode(),SysOptError.SysError.getName()+"[查询结果大于1个]");
	 return result.size() ==0 ? null:result.get(0);
				
	}
	

}
