package cn.oxo.iworks.builds.base;

public class BuildDaoBean {

	private String className;

	private String basePath;
	
	

	private Class<?> clazz;

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getBasePath() {
	    return basePath;
	}

	public void setBasePath(String basePath) {
	    this.basePath = basePath;
	}

	public Class<?> getClazz() {
	    return clazz;
	}

	public void setClazz(Class<?> clazz) {
	    this.clazz = clazz;
	}

	
 
	
}
