package cn.oxo.iworks.web.shiro.simple;

import cn.oxo.iworks.databases.SystemOptServiceException;
import cn.oxo.iworks.web.shiro.ClientInfoBean;

public interface IShiroExClientService {

   
    public ShiroClientInfoBean loginByClient(ClientUserLoginBean wxUserInfoBean) throws SystemOptServiceException;
    
    public ClientInfoBean loginByManager(String access) throws SystemOptServiceException;

}
