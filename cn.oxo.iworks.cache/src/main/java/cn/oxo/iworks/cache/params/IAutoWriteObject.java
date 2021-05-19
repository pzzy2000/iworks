package cn.oxo.iworks.cache.params;

import java.lang.reflect.Field;

public abstract class IAutoWriteObject {

      protected boolean isSetNull = false;

      public IAutoWriteObject(boolean isSetNull) {
            this.isSetNull = isSetNull;
      }

      protected AutoWritePrpertiesObjectService iAutoWritePrpertiesObjectService;

      public void setiAutoWritePrpertiesObjectService(AutoWritePrpertiesObjectService iAutoWritePrpertiesObjectService) {
            this.iAutoWritePrpertiesObjectService = iAutoWritePrpertiesObjectService;
      }

      public String getIdSuffix() {
            return iAutoWritePrpertiesObjectService.getIdSuffix();
      }

      public abstract Object handField(Field field, Object result);

}
