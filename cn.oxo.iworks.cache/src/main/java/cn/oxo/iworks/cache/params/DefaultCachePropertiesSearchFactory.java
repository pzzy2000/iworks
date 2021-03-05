//package cn.oxo.iworks.cache.params;
//
//import java.io.Serializable;
//
//import org.apache.commons.lang3.reflect.FieldUtils;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import cn.zy.dev.tools.cache.ICacheFactory;
//
//public class DefaultCachePropertiesSearchFactory implements IPropertiesSearchFactory {
//    
//        private Logger  logger =LogManager.getLogger(DefaultCachePropertiesSearchFactory.class);
//
//	private ICacheFactory cacheFactory;
//	 
//	private String idfield;
//
//	public DefaultCachePropertiesSearchFactory(String idfield ,ICacheFactory cacheFactory) {
//		super();
//		this.cacheFactory = cacheFactory;
//		this.idfield = idfield;
//	}
//	
//	public DefaultCachePropertiesSearchFactory(ICacheFactory cacheFactory) {
//		super();
//		this.cacheFactory = cacheFactory;
//		this.idfield = "id";
//	}
//
//	
//	@Override
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	public <V extends Serializable> V searchObjectById(String id, Class clazz) {
//	    try {
//	        V v= (V) ( clazz.newInstance());
//	        FieldUtils.writeField(v, idfield, id, true);;
//		return (V) cacheFactory.get(v, clazz);
//	    }catch (Exception e) {
//		logger.error(e.getMessage(),e);
//		return null;
//	    }
//
//	}
//
//}
