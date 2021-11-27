package cn.oxo.iworks.web.shiro.bak;

import cn.oxo.iworks.databases.SystemOptServiceException;
import cn.oxo.iworks.web.shiro.ClientInfoBean;

public interface IShrioClientService {

      public ClientInfoBean searchClient(String access, LoginType loginType) throws SystemOptServiceException;

}
