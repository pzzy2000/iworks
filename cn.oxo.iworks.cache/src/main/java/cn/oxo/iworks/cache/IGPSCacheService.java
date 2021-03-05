package cn.oxo.iworks.cache;

public interface IGPSCacheService {
	
	
	public void put(Object value);
	
	
	public void clear(Object value);
	
	/**
	 * 将 key，value 存放到redis数据库中，默认设置过期时间为一周
	 *
	 * @param key
	 * @param value
	 */
	public void set(Object key, String field, Object value);
	
	/**
	 * 将 key，value 存放到redis数据库中，设置过期时间单位是秒
	 *
	 * @param key
	 * @param value
	 * @param expireTime
	 */

	public void set(Object key, String field, Object value, long expireTime);
	
	
	public <V> boolean exists(Object key, String field, Class<V> clazz);
	
	public <V> V get(Object key, String field, Class<V> clazz);
	
	public  <V>  void delete(String key, String field, Class<V> clazz);

}
