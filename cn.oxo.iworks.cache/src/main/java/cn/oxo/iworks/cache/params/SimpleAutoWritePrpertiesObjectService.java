package cn.oxo.iworks.cache.params;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 简单的设置对象的服务
 * 
 * @author youss
 * 
 */ 

public abstract class SimpleAutoWritePrpertiesObjectService extends AutoWritePrpertiesObjectService {

	private IAutoWriteObject iAutoWriteObject;

	public SimpleAutoWritePrpertiesObjectService(String idSuffix, List<String> regexPackage, IAutoWriteObject iAutoWriteObject) {
		super(idSuffix,regexPackage);
		this.iAutoWriteObject = iAutoWriteObject;
		iAutoWriteObject.setiAutoWritePrpertiesObjectService(this);

	}

	@Override
	protected Object handField(Field field, Object result) {
		return iAutoWriteObject.handField(field, result);

	}

}
