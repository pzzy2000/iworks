package cn.oxo.iworks.cache;

import cn.oxo.iworks.cache.IGPSCacheService;

public interface IRedisService extends IGPSCacheService {

    public String default_String_null = "isnulls";
    
    
    public void putByGeneral(String key,Object value);
    
    public Object getByGeneral(String key);
}
