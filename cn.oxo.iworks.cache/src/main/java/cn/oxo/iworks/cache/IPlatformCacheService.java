package cn.oxo.iworks.cache;

import java.io.Serializable;

public interface IPlatformCacheService extends IGPSCacheService {
	
	public String name="IPlatformRedisService";
	
	
	public <V extends Serializable> V searchObjectById(String id, Class<V> clazz);
	
}
