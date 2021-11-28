package cn.oxo.iworks.web.shiro.simple;

import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.cache.CacheException;
import org.redisson.api.RedissonClient;

public class AuthorizationInfoCache extends RedissonShiroCache<Object, AuthorizationInfo> {

      public AuthorizationInfoCache(RedissonClient redissonClient) {
            super(redissonClient.getMap("gpsc:shiro:authorizationinfo"), false);

      }

      @Override
      public AuthorizationInfo get(Object key) throws CacheException {

            return super.get(key);
      }

      @Override
      public AuthorizationInfo put(Object key, AuthorizationInfo value) throws CacheException {

            return super.put(key, value);
      }

}
