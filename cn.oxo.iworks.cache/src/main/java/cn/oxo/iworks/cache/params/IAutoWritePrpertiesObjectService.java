package cn.oxo.iworks.cache.params;

import org.aspectj.lang.JoinPoint;

public interface IAutoWritePrpertiesObjectService {

      /**
       * 设置对象字段属性
       * 
       * @param result
       * @throws Exception
       */
      public void setPrperties(Object result) throws Exception;

      public void setPrperties(JoinPoint jp, Object result) throws Exception;

}
