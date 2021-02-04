package cn.oxo.iworks.web.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.cache.CacheException;
import org.redisson.api.RedissonClient;

public class AuthenticationInfoCache extends RedissonShiroCache<Object, AuthenticationInfo> {

	public AuthenticationInfoCache(RedissonClient redissonClient) {
		super(redissonClient.getMap("gpsc:shiro:authenticationifo"), false);

	}

	@Override
	public AuthenticationInfo get(Object key) throws CacheException {
		// TODO Auto-generated method stub
		return super.get(key);
	}

	@Override
	public AuthenticationInfo put(Object key, AuthenticationInfo value) throws CacheException {

		return super.put(key, value);
	}

}
