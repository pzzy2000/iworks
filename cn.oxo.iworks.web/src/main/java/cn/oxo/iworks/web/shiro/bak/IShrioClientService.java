package cn.oxo.iworks.web.shiro.bak;

import cn.oxo.iworks.databases.SystemOptServiceException;

public interface IShrioClientService {

      public ClientInfoBean searchClient(String access, LoginType loginType) throws SystemOptServiceException;

}
