package cn.oxo.iworks.cache;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;

import com.alibaba.fastjson.JSON;

@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
public class RedisGPSCacheService extends GPSCacheService {

	private RedisTemplate redisTemplate;

	public RedisGPSCacheService(RedisTemplate redisTemplate) {
		super();
		this.redisTemplate = redisTemplate;
	}

	@Override
	public void set(Object key, String field, Object value) {

		if (value == null)
			return;

		String jsonValue = JSON.toJSONString(value);

		redisTemplate.opsForValue().set(createKey(value.getClass(), field, key), jsonValue, expireTime(),
				TimeUnit.SECONDS);

	}

	@Override
	public void set(Object key, String field, Object value, long expireTime) {
		if (value == null)
			return;

		String jsonValue = JSON.toJSONString(value);

		redisTemplate.opsForValue().set(createKey(value.getClass(), field, key), jsonValue, expireTime,
				TimeUnit.SECONDS);

	}

	@Override
	public <V> boolean exists(Object key, String field, Class<V> clazz) {
		return redisTemplate.hasKey(createKey(clazz, field, key));
	}

	@Override
	public <V> V get(Object key, String field, Class<V> clazz) {
		String json = (String) redisTemplate.opsForValue().get(createKey(clazz, field, key));
		return JSON.toJavaObject(JSON.parseObject(json), clazz);
	}

	@Override
	public <V> void delete(String key, String field, Class<V> clazz) {
		redisTemplate.delete(createKey(clazz, field, key));

	}

}
