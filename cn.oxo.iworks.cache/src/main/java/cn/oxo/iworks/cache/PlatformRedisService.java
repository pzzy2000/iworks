package cn.oxo.iworks.cache;

import java.io.Serializable;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.uorm.DefaultSearch;
import org.uorm.ISearch;

public class PlatformRedisService implements IPlatformCacheService {

	private Logger logger = LogManager.getLogger(PlatformRedisService.class);

	protected IGPSCacheService cacheService;

	protected ISearch search;

	public PlatformRedisService(DataSource dataSource, IGPSCacheService cacheService) {
		super();
		this.search = new DefaultSearch(dataSource);
		this.cacheService = cacheService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <V extends Serializable> V searchObjectById(String id, Class<V> clazz) {
		if (StringUtils.isEmpty(id))
			return null;
		Serializable v = cacheService.get(id, "id", clazz);
		if (v == null) {
			try {
				v = search.search(clazz, "id", id);
				if (v != null) {
					cacheService.set(id, "id", v);
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				v = null;
			}
		}
		return (V) v;

	}

	@Override
	public void put(Object value) {
		cacheService.put(value);
	}

	@Override
	public void clear(Object value) {
		cacheService.clear(value);

	}

	@Override
	public void set(Object key, String field, Object value) {
		cacheService.set(key, field, value);

	}

	@Override
	public void set(Object key, String field, Object value, long expireTime) {
		cacheService.set(key, field, value, expireTime);

	}

	@Override
	public <V> boolean exists(Object key, String field, Class<V> clazz) {

		return cacheService.exists(key, field, clazz);
	}

	@Override
	public <V> V get(Object key, String field, Class<V> clazz) {

		return cacheService.get(key, field, clazz);
	}

	@Override
	public <V> void delete(String key, String field, Class<V> clazz) {
		cacheService.delete(key, field, clazz);
	}

}
