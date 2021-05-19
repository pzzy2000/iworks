package cn.oxo.iworks.cache.params;

import java.lang.reflect.Field;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DefaultIdAutoWriteObject extends AutoWriteObject {

      protected Logger logger = LogManager.getLogger(DefaultIdAutoWriteObject.class);

      private IPropertiesSearchFactory iIPropertiesCacheFactory;

      public DefaultIdAutoWriteObject(IPropertiesSearchFactory iIPropertiesCacheFactory, boolean isSetNull) {
            super(isSetNull);
            this.iIPropertiesCacheFactory = iIPropertiesCacheFactory;
      }

      public DefaultIdAutoWriteObject(IPropertiesSearchFactory iIPropertiesCacheFactory) {
            super(true);

            this.iIPropertiesCacheFactory = iIPropertiesCacheFactory;
      }

      @Override
      public Object handField(Field field, Object result) {
            // bean
            if (field.getName().endsWith(getIdSuffix())) {
                  try {
                        String fieldName = field.getName();
                        // id
                        String idfieldName = fieldName.substring(0, fieldName.length() - getIdSuffix().length()) + "Id";

                        Object idValue = FieldUtils.readField(result, idfieldName, true);

                        if (idValue == null)
                              return null;
                        else {
                              Object iobject = iIPropertiesCacheFactory.searchObjectById(idValue.toString(), field.getType());

                              return iobject;
                        }

                  } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                        return null;
                  }

            } else {
                  return null;
            }
      }

}
