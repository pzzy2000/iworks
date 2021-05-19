package org.uorm;

import java.io.Serializable;

public interface ISearch {

      public <V extends Serializable> Serializable search(Class<V> clazz, String idFields, Serializable idValue) throws Exception;

}
