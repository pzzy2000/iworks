package cn.oxo.iworks.cache.params;

import java.io.Serializable;

public interface IPropertiesSearchFactory {

	
	public <V extends Serializable> V searchObjectById(String id, @SuppressWarnings("rawtypes") Class clazz);

}
