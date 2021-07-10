package org.uorm;

import java.io.Serializable;

public interface ISearch {

    public Serializable search(Class<Serializable> clazz, String idFields, Serializable idValue) throws Exception;

}
