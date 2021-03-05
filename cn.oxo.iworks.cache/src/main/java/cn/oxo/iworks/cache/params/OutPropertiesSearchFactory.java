// package cn.zy.dev.tools.params;
//
// import cn.oxo.dev.tools.cache.CacheDif;
// import cn.oxo.dev.tools.cache.ICacheObjectService;
// import cn.oxo.dev.tools.cache.ICacheSearchObjectService;
//
// public abstract class OutPropertiesSearchFactory extends
// ABPropertiesSearchFactory {
//
// protected ICacheSearchObjectService iICacheSearchObjectService;
//
// protected ICacheObjectService iICacheObjectService;
//
// public OutPropertiesSearchFactory(ICacheSearchObjectService
// iICacheSearchObjectService, ICacheObjectService iICacheObjectService) {
// super();
// this.iICacheSearchObjectService = iICacheSearchObjectService;
//
// this.iICacheObjectService = iICacheObjectService;
// }
//
// protected abstract String getManKey(String cachekey, Class<?> clazz, String
// id);
//
// protected abstract Object searchByDb(String id, Class<?> clazz);
//
// protected abstract Object cacheObject(Object result, ICacheObjectService
// iICacheObjectService);
//
// @Override
// protected Object searchObject(String id, Class<?> clazz) {
//
// if (!clazz.getClass().isAnnotationPresent( CacheDif.class ))
// return null;
//
// CacheDif iCacheDif = clazz.getAnnotation( CacheDif.class );
//
// String key = getManKey( iCacheDif.idKey(), clazz, id );
//
// Object object = iICacheSearchObjectService.searchObject( key, clazz );
//
// if (object == null) {
// object = searchByDb( id, clazz );
// if (object != null) {
//
// cacheObject( object, iICacheObjectService );
//
// }
//
// }
//
// return object;
// }
//
// }
