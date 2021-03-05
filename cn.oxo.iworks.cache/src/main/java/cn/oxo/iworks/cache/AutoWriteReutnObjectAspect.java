package cn.oxo.iworks.cache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;

import cn.oxo.iworks.cache.params.DefaultSimpleIdAutoWritePrpertiesObjectService;
import cn.oxo.iworks.cache.params.IAutoWritePrpertiesObjectService;
import cn.oxo.iworks.cache.params.IPropertiesSearchFactory;

/*
 * @Component
   @Aspect
   @Order(1)
    @AfterReturning(value = "execution(public * cn.oxo.cloudstore..controller.aop..IAop*Service.*(..))", returning = "result")
 */
public abstract class AutoWriteReutnObjectAspect {

	private Logger logger = LogManager.getLogger(AutoWriteReutnObjectAspect.class);

	private IAutoWritePrpertiesObjectService autoWritePrpertiesObjectService;

	protected IPlatformCacheService platformCacheService;

//	@AfterReturning(value = "execution(public * cn.oxo.cloudstore..controller.aop..IAop*Service.*(..))", returning = "result")
//	public void sss(JoinPoint jp, Object result) {
//		try {
//			// System.out.println(">>>>>>>>>>>>>>>>>>>> 1121212 :result" + result.getClass()
//			// + "result " + result);
//			autoWritePrpertiesObjectService.setPrperties(result);
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//		}
//	}

	// @AfterReturning(value = "execution(public * cn.oxo.cloudstore..controller.aop..IAop*Service.*(..))", returning = "result")
	public void setParams(JoinPoint jp, Object result) {
		try {
			autoWritePrpertiesObjectService.setPrperties(result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public AutoWriteReutnObjectAspect(IPlatformCacheService platformCacheService) {
		super();
		this.platformCacheService = platformCacheService;
	}

	protected abstract void  regexPackage(List<String> bean);

	@PostConstruct
	private void init() {

		List<String> bean = new ArrayList<String>();

		regexPackage(bean);

		IAutoWritePrpertiesObjectService autoWritePrpertiesObjectService = new DefaultSimpleIdAutoWritePrpertiesObjectService(
				bean, "Bean", new IPropertiesSearchFactory() {
					@SuppressWarnings({ "unchecked", "rawtypes" })
					@Override
					public <V extends Serializable> V searchObjectById(String id, Class clazz) {
						
						Serializable iSerializable = platformCacheService.searchObjectById(id, clazz);
                       
						return (V) iSerializable;

					}
				}, true);

		setAutoWritePrpertiesObjectService(autoWritePrpertiesObjectService);

	}

	private void setAutoWritePrpertiesObjectService(IAutoWritePrpertiesObjectService autoWritePrpertiesObjectService) {
		this.autoWritePrpertiesObjectService = autoWritePrpertiesObjectService;
	}

}
