package cn.oxo.iworks.cache;

import org.springframework.beans.factory.annotation.Autowired;

import cn.oxo.iworks.databases.ABBaseUnits;

public class ABBaseCacheUnits extends ABBaseUnits {

	@Autowired
	protected IGPSCacheService cacheService;

	public void clear(Object value) {
		cacheService.clear(value);
	}

}
