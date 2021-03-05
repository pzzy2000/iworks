package cn.oxo.iworks.cache.params;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.hibernate.collection.internal.PersistentBag;


public abstract class AutoWritePrpertiesObjectService implements IAutoWritePrpertiesObjectService {

	protected Logger logger = LogManager.getLogger(AutoWritePrpertiesObjectService.class);
	
	private Map<Class<?>,List<Field> > iFieldListMap =new ConcurrentHashMap<Class<?>,List<Field> >();

	private List<String> regexPackages;

	private String idSuffix;

	/**
	 *  
	 * @param regexPackages
	 *            正则要设置的对象的包名
	 */
	public AutoWritePrpertiesObjectService(String idSuffix, List<String> regexClass) {

		this.regexPackages = regexClass;

		this.idSuffix = idSuffix;

		logger.info("load  Prperties   idSuffix    : " + idSuffix);

		printRegexPackage(regexPackages);

	}

	private void printRegexPackage(List<String> regexPackages) {
		for (String regexPackage : regexPackages) {
			logger.info( "load  Prperties   regexPackage    : " + regexPackage);
		}

	}

	
	private List<Field>  getFieldList(Object result) {
		List<Field>  iFieldList =	iFieldListMap.get(result.getClass());
		if(iFieldList ==null) {
			iFieldList = FieldUtils.getAllFieldsList(result.getClass());
			iFieldListMap.put(result.getClass(), iFieldList);
		}
		return iFieldList;
	}
	

	/**
	 * 
	 */
	@Override
	public void setPrperties(Object result) throws Exception {
		if (result == null)
			return;
		if (result != null) {
			if (result instanceof List<?>) {
				List<?> resultList = (List<?>) result;
				for (Object object : resultList) {
					simpleObject(object, result);
				}
			} else {
				simpleObject(result, null);
			}
		}
	}

	/**
	 * 验证 class 是否匹配
	 * 
	 * @param clzz
	 * @return
	 */
	private boolean vpackage(Class<?> clzz) {
		if (clzz.getPackage() == null) {
			return false;
		} else {
			if (regexPackages == null || regexPackages.size() == 0)
				return true;
			boolean result = false;
			for (String regexPackage : regexPackages) {
				result = clzz.getName().startsWith(regexPackage);
				if (result == true)
					break;
			}

			return result;
		}
	}

	private void simpleObject(Object result, Object parent) {
		if (result == null || !vpackage(result.getClass()))
			return;

		List<Field> iFieldList = getFieldList(result);

		for (Field field : iFieldList) {
			Class<?> clzz = field.getType();
			if (clzz.equals(Set.class) || clzz.equals(List.class)) {
				try {
					Object results = FieldUtils.readField(field, result, true);
					if (results == null) {
						continue;
					} else {
						for (Object object : (List<?>) results) {
							simpleObject(object, result);
						}
					}
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
			} else {
				if (vpackage(clzz)) {
					try {
						Object objs = FieldUtils.readField(field, result, true);
						if (objs == null || objs.getClass().toString().contains("$$_javassist_")
								|| objs.getClass().toString().contains("_$$_jvst")) {
							// 付值
							if (field.getName().endsWith(idSuffix)) {
								objs=handField(field, result);
								FieldUtils.writeField(field, result, objs, true);
							}
						}
						if(objs != null){
							if (objs.equals(parent)) {
							} else {
								simpleObject(objs, result);
							}
						}

					} catch (Exception e) {
						logger.error(e.getMessage());
					}

				} else {
					continue;
				}
			}
		}

	}

	public String getIdSuffix() {
		return idSuffix;
	}

	/**
	 * 处理字段
	 * 
	 * @param fieldName
	 * @param result
	 */
	protected abstract Object handField(Field field, Object result);

}