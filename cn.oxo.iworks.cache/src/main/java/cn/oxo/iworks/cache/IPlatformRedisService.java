package cn.oxo.iworks.cache;

import java.io.Serializable;

public interface IPlatformRedisService {

    public String name = "IPlatformRedisService";

    @SuppressWarnings("rawtypes")
    public <V extends Serializable> V searchObjectById(String id, Class clazz);

    public void clearCache(Object result);

    public void cache(Object result);

    public void set(Object key, String field, Object value);

    public <V> V findBy(Object key, String field, Class<V> clazz);

    public void putByGeneral(String key, Object value);

    public Object getByGeneral(String key);

}
