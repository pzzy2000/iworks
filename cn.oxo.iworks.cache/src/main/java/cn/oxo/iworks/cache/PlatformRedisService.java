package cn.oxo.iworks.cache;

import java.io.Serializable;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;

public class PlatformRedisService implements IPlatformRedisService {

	private Logger logger = LogManager.getLogger(PlatformRedisService.class);

	protected IRedisService redisService;

	protected IDBCacheSearch dbCacheSearch;

	@SuppressWarnings("rawtypes")
	public PlatformRedisService(RedisTemplate redisTemplate, DataSource dataSource) {
		super();
		this.redisService = new RedisService(redisTemplate);
		this.dbCacheSearch = new DefaultDBCacheSearch(dataSource);
		;
	}

	public PlatformRedisService(IRedisService redisService, DataSource dataSource) {
		super();
		this.redisService = redisService;
		this.dbCacheSearch = new DefaultDBCacheSearch(dataSource);
		;
	}

	public PlatformRedisService(IRedisService redisService, IDBCacheSearch dbCacheSearch) {
		super();
		this.redisService = redisService;
		this.dbCacheSearch = dbCacheSearch;
	}

	@Override
	public void clearCache(Object result) {

		redisService.clear(result);

	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <V extends Serializable> V searchObjectById(String id, Class clazz) {

		if (StringUtils.isEmpty(id))
			return null;

		V v = (V) redisService.get(id, "id", clazz);
		if (v == null) {
			try {

				Serializable iSerializable = dbCacheSearch.search(clazz, "id", id);
				if (iSerializable != null) {
					v = (V) iSerializable;
					redisService.set(id, "id", v);
				}
			} catch (Exception e) {
				v = null;
				logger.error(e.getMessage(), e);
			}
		}
		return v;
	}

	@Override
	public <V> V findBy(Object key, String field, Class<V> clazz) {
		V v = redisService.get(key, field, clazz);
		return v;
	}

	@Override
	public void cache(Object result) {
		redisService.put(result);

	}

	@Override
	public void set(Object key, String field, Object value) {
		redisService.set(key, field, value);
	}

}
