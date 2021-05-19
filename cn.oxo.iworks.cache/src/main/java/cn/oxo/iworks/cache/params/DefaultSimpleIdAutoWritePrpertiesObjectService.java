package cn.oxo.iworks.cache.params;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.lang3.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * 缓存方式设置字段服务
 * 
 * @author you
 * 
 */
public class DefaultSimpleIdAutoWritePrpertiesObjectService extends SimpleAutoWritePrpertiesObjectService {

      protected Logger logger = LogManager.getLogger(DefaultSimpleIdAutoWritePrpertiesObjectService.class);

      public DefaultSimpleIdAutoWritePrpertiesObjectService(List<String> regexPackage, String idSuffix, IPropertiesSearchFactory propertiesSearchFactory, boolean isSetNull) {
            super(idSuffix, regexPackage, new DefaultIdAutoWriteObject(propertiesSearchFactory, isSetNull));

      }

      public DefaultSimpleIdAutoWritePrpertiesObjectService(List<String> regexPackage, String idSuffix, IPropertiesSearchFactory propertiesSearchFactory) {
            super(idSuffix, regexPackage, new DefaultIdAutoWriteObject(propertiesSearchFactory, false));

      }

      @Override
      public void setPrperties(JoinPoint jps, Object result) throws Exception {

            if ((jps.getSignature() instanceof MethodSignature) == false)
                  return;

            Class<?> targetClass = jps.getTarget().getClass();

            MethodSignature methodSignature = (MethodSignature) jps.getSignature();

            methodSignature.getMethod();

            Method method = methodSignature.getMethod();

            List<Class<?>> interfaceClasses = ClassUtils.getAllInterfaces(targetClass);
            Method iMethod = null;
            for (Class<?> clazz : interfaceClasses) {
                  try {
                        iMethod = ClassUtils.getPublicMethod(clazz, method.getName(), method.getParameterTypes());
                        if (iMethod.isAnnotationPresent(CacheWirte.class)) {
                              break;
                        } else {
                              iMethod = null;
                        }
                  } catch (NoSuchMethodException e) {
                        logger.error("not find class -> " + targetClass.getName() + " method -> " + method.getName());
                  }
            }
            if (iMethod != null) {
                  logger.info("write cache to object > method : " + method.getName() + "  ->   " + result.toString());
                  setPrperties(result);
            }

      }

}
