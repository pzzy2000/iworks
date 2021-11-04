package cn.oxo.iworks.cache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.springframework.data.redis.core.RedisTemplate;

import cn.oxo.iworks.cache.params.DefaultSimpleIdAutoWritePrpertiesObjectService;
import cn.oxo.iworks.cache.params.IAutoWritePrpertiesObjectService;
import cn.oxo.iworks.cache.params.IPropertiesSearchFactory;

/*
 * @Component
 * @Aspect
 * @Order(1)
 * @AfterReturning(value ="execution(public * cn.oxo.cloudstore..controller.aop..IAop*Service.*(..))",returning = "result")
 */
public abstract class AutoWriteReutnObjectAspect {

      private Logger logger = LogManager.getLogger(AutoWriteReutnObjectAspect.class);

      private IAutoWritePrpertiesObjectService autoWritePrpertiesObjectService;

      protected IPlatformRedisService platformRedisService;
      /**
       * 
       * @param jp
       * @param result
       * @AfterReturning(value ="execution(public * cn.oxo.cloudstore..controller.aop..IAop*Service.*(..))",returning = "result")
       */
      public abstract void setBeanParams(JoinPoint jp, Object result);

      protected void setParams(JoinPoint jp, Object result) {
            try {
                  autoWritePrpertiesObjectService.setPrperties(result);
            } catch (Exception e) {
                  logger.error(e.getMessage(), e);
            }
      }

      public AutoWriteReutnObjectAspect(IPlatformRedisService platformRedisService) {
         
            this.platformRedisService = platformRedisService;
      }
      
      @SuppressWarnings("rawtypes")
	public AutoWriteReutnObjectAspect(RedisTemplate redisTemplate, DataSource dataSource) {
          
          this.platformRedisService = new PlatformRedisService(redisTemplate,dataSource);
    }

      protected abstract void regexPackage(List<String> bean);

      @PostConstruct
      private void init() {

            List<String> bean = new ArrayList<String>();

            regexPackage(bean);

            IAutoWritePrpertiesObjectService autoWritePrpertiesObjectService = new DefaultSimpleIdAutoWritePrpertiesObjectService(bean, "Bean", new IPropertiesSearchFactory() {
                  @SuppressWarnings({ "unchecked", "rawtypes" })
                  @Override
                  public <V extends Serializable> V searchObjectById(String id, Class clazz) {

                        Serializable iSerializable = platformRedisService.searchObjectById(id, clazz);

                        return (V) iSerializable;

                  }
            }, true);

            setAutoWritePrpertiesObjectService(autoWritePrpertiesObjectService);

      }

      private void setAutoWritePrpertiesObjectService(IAutoWritePrpertiesObjectService autoWritePrpertiesObjectService) {
            this.autoWritePrpertiesObjectService = autoWritePrpertiesObjectService;
      }

}
