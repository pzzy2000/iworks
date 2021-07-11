package cn.oxo.iworks.builds.test.service.mybatis;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.oxo.iworks.builds.test.bean.CustomerBean;
import cn.oxo.iworks.builds.test.bean.search.CustomerSearchBean;
import cn.oxo.iworks.databases.CommSearchBean;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface CustomerBaseMapper extends BaseMapper<CustomerBean> {
 
 
 public List<CustomerBean> searchCustomerSelective(Page<CustomerBean> page ,@Param("searchBean") CustomerSearchBean searchBean , @Param("commSearchBean") CommSearchBean commSearchBean );
 
 public List<CustomerBean> searchCustomerSelective(@Param("searchBean") CustomerSearchBean searchBean , @Param("commSearchBean") CommSearchBean commSearchBean );
 
 public int deleteByPrimaryKey(Long id);

 public int insertSelective(CustomerBean customerbean);

 public CustomerBean selectByPrimaryKey(Long id);
 
 public int updateByPrimaryKeySelective(CustomerBean customerbean);

}