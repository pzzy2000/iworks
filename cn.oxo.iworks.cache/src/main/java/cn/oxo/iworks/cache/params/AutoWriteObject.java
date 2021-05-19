package cn.oxo.iworks.cache.params;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AutoWriteObject extends IAutoWriteObject {

      public AutoWriteObject(boolean isSetNull) {
            super(isSetNull);
      }

      protected Logger logger = LogManager.getLogger(AutoWriteObject.class);

}
