package cn.oxo.iworks.cache.params;

import java.io.Serializable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class ABPropertiesSearchFactory implements IPropertiesSearchFactory {

	protected Logger logger = LogManager.getLogger(ABPropertiesSearchFactory.class);

	protected abstract Object searchObject(String id, Class<?> clazz);

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <V extends Serializable> V searchObjectById(String id, Class clazz) {

		return (V) searchObject(id, clazz);
	}

}
