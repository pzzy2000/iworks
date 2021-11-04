package cn.oxo.iworks.cache;

import java.io.Serializable;

public interface IDBCacheSearch {

    public String name = "IDBCacheSearch";

    public Serializable search(Class<Serializable> clazz, String idFields, Serializable idValue) throws Exception;

}
