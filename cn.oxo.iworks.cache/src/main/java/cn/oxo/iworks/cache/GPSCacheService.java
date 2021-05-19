package cn.oxo.iworks.cache;

import java.lang.reflect.Field;

public abstract class GPSCacheService implements IGPSCacheService {

      protected long expireTime() {
            return (int) (Math.random() * (9999 - 1000 + 1) + 1000);
      }

      protected <V> String createKey(Class<V> clazz, String field, Object key) {
            if (clazz.isAnnotationPresent(GPSCCache.class)) {
                  GPSCCache iGPSCCache = clazz.getAnnotation(GPSCCache.class);
                  return "gpsc:db:" + iGPSCCache.store() + ":" + field + ":" + key.toString();
            } else {
                  throw new RuntimeException("class " + clazz.getName() + "  not find  @GPSCCache ");
            }

      }

      @Override
      public void put(Object value) {

            if (value == null)
                  return;
            if (value.getClass().isAnnotationPresent(GPSCCache.class) == false) {
                  throw new RuntimeException("cache object " + value.getClass() + "  not find @GPSCCache");
            }
            GPSCCache iGPSCCache = value.getClass().getAnnotation(GPSCCache.class);

            for (String field : iGPSCCache.cachefield()) {
                  try {
                        Field iField = value.getClass().getDeclaredField(field);
                        iField.setAccessible(true);
                        Object id = iField.get(value);
                        if (id == null) {
                              throw new RuntimeException("field : " + field + " result ： " + value.getClass() + " value null !");
                        } else {
                              set(id.toString(), field, value);
                        }

                  } catch (Exception e) {
                        throw new RuntimeException(e);
                  }

            }

      }

      @Override
      public void clear(Object value) {

            if (value == null)
                  return;
            if (value.getClass().isAnnotationPresent(GPSCCache.class) == false) {
                  throw new RuntimeException("cache object " + value.getClass() + "  not find @GPSCCache");
            }
            GPSCCache iGPSCCache = value.getClass().getAnnotation(GPSCCache.class);

            for (String field : iGPSCCache.cachefield()) {
                  try {
                        Field iField = value.getClass().getDeclaredField(field);
                        iField.setAccessible(true);
                        Object id = iField.get(value);
                        if (id == null) {
                              throw new RuntimeException("field : " + field + " result ： " + value.getClass() + " value null !");
                        } else {
                              delete(id.toString(), field, value.getClass());
                        }

                  } catch (Exception e) {
                        throw new RuntimeException(e);
                  }

            }

      }

}
