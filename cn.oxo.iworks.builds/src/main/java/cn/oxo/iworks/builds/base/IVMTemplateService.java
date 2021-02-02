package cn.oxo.iworks.builds.base;

public abstract class IVMTemplateService {
	
	   public String mapperVm() {
			String vm = "template/java/Mapper.vm";
			return vm;
	   }
	   
	   public String searchBeanVm() {
		   String vm = "template/java/SearchBean.vm";
		   return vm;
	   }
	   
	   public String searchMapperXmlVm() {
		   String vm = "template/java/Mapper.xml.vm";
		   return vm;

	   }
	   
	   public String infaceServiceJavaVm() {
		   String vm = "template/java/InfaceService.java.vm";
		   return vm;
	   }
	   
	   public String iAopServiceJavaVm() {
		   String vm = "template/java/IAopService.java.vm";
		   return vm;
	   }
	   
	   public String aopServiceJavaVm() {
		   String vm = "template/java/AopService.java.vm";
		   return vm;
	   }
	   
	   public String actionJavaVm() {
		   String vm = "template/java/Action.java.vm";
		   return vm;
	   }
	   
	   public String iserviceImpleJavaVm(){
		   String vm = "template/java/IServiceImple.java.vm";
		   return vm;
	   }

}
