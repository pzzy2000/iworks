package cn.oxo.iworks.builds.base;

import java.util.HashMap;
import java.util.Map;

import cn.oxo.iworks.builds.VelocityToolsUnits;
import cn.oxo.iworks.builds.db.DBUtils;
import cn.oxo.iworks.builds.html.CreateHtml;

public abstract class ABBuildService implements IBuildService {

	private CreateHtml iCreateHtml = new CreateHtml();

	private String basePath;

	private boolean createHtml = false;

	public ABBuildService(String basePath, boolean isCreateHtml) {
		super();
		this.basePath = basePath;
		this.createHtml = isCreateHtml;

	}

	@Override
	public TableBean init(Class<?> clazz) throws MyBatisBuildServiceException {

		ScanTableBean iScanTableBean = new ScanTableBean();

		TableBean iTableBean = iScanTableBean.scan(clazz);

		return iTableBean;

	}

	@Override
	public void build(TableBean tableBean, String outPath) throws MyBatisBuildServiceException {

		buildMapper(tableBean, tableBean.getPojo(), outPath);

		buildMapperXml(tableBean, tableBean.getPojo(), outPath);

		buildSearchBean(tableBean, tableBean.getPojo(), outPath);

		buildAop(tableBean, tableBean.getPojo(), outPath);

		buildSaveUpdateUnits(tableBean, tableBean.getPojo(), outPath);

		buildSearchUnits(tableBean, tableBean.getPojo(), outPath);

		buildService(tableBean, tableBean.getPojo(), outPath);

		buildAction(tableBean, tableBean.getPojo(), outPath);

//		buildInface(tableBean, tableBean.getPojo(), outPath);

		if (createHtml == true) {
			try {
				iCreateHtml.create(tableBean.getPojo(), outPath);
			} catch (Exception e) {
				throw new MyBatisBuildServiceException(e);
			}
		}

	}

	@Override
	public void createTable(TableBean tableBean, DBUtils dbutils) throws MyBatisBuildServiceException {

		buildCrate(tableBean, tableBean.getPojo(), dbutils);

	}

	private Map<String, Object> createVParams(TableBean iTableBean, Class<?> clazz) {
		Map<String, Object> values = new HashMap<String, Object>();

		BuildDaoBean iBuildDaoBean = new BuildDaoBean();

		iBuildDaoBean.setClassName(clazz.getSimpleName());

		iBuildDaoBean.setClazz(clazz);

		iBuildDaoBean.setBasePath(basePath);

		values.put("BuildDaoBean", iBuildDaoBean);

		values.put("TableBean", iTableBean);

		return values;

	}

	protected void buildVueApi(TableBean iTableBean, Class<?> clazz, String outPath)
			throws MyBatisBuildServiceException {

		Map<String, Object> values = createVParams(iTableBean, clazz);

		String vm = "template/vue/api.js";

		try {
			VelocityToolsUnits.toFile(vm, values, outPath + "/vue/" + clazz.getSimpleName() + ".js");
		} catch (Exception e) {
			throw new MyBatisBuildServiceException(e);
		}
	}

	protected void buildMapper(TableBean iTableBean, Class<?> clazz, String outPath)
			throws MyBatisBuildServiceException {

		Map<String, Object> values = createVParams(iTableBean, clazz);

		String vm = "template/java/Mapper.vm";

		try {
			VelocityToolsUnits.toFile(vm, values,
					outPath + "/service/mybatis/" + clazz.getSimpleName().substring(0, clazz.getSimpleName().length() - 4) + "BaseMapper.java");
		} catch (Exception e) {
			throw new MyBatisBuildServiceException(e);
		}
	}

	protected void buildSearchBean(TableBean iTableBean, Class<?> clazz, String outPath)
			throws MyBatisBuildServiceException {

		Map<String, Object> values = createVParams(iTableBean, clazz);

		String vm = "template/java/SearchBean.vm";

		try {
			VelocityToolsUnits.toFile(vm, values, outPath + "/bean/search/"
					+ clazz.getSimpleName().substring(0, clazz.getSimpleName().length() - 4) + "SearchBean.java");
		} catch (Exception e) {
			throw new MyBatisBuildServiceException(e);
		}
	}

	protected void buildMapperXml(TableBean iTableBean, Class<?> clazz, String outPath, String vmfile)
			throws MyBatisBuildServiceException {

		Map<String, Object> values = createVParams(iTableBean, clazz);

		try {
			VelocityToolsUnits.toFile(vmfile, values, outPath + "/mapper/" + clazz.getSimpleName() + "BaseMapper.xml");
		} catch (Exception e) {
			throw new MyBatisBuildServiceException(e);
		}
	}

	protected void buildMapperXml(TableBean iTableBean, Class<?> clazz, String outPath)
			throws MyBatisBuildServiceException {

		String vmfile = "template/java/Mapper.xml.vm";

		buildMapperXml(iTableBean, clazz, outPath, vmfile);
	}

	protected void buildInface(TableBean iTableBean, Class<?> clazz, String outPath)
			throws MyBatisBuildServiceException {

		Map<String, Object> values = createVParams(iTableBean, clazz);

		try {
			String vm = "template/java/InfaceService.java.vm";

			String aopfileName = "Inface" + clazz.getSimpleName() + "Service.java";
			VelocityToolsUnits.toFile(vm, values, outPath + "/inface/" + aopfileName);

		} catch (Exception e) {
			throw new MyBatisBuildServiceException(e);
		}

	}

	protected void buildAop(TableBean iTableBean, Class<?> clazz, String outPath) throws MyBatisBuildServiceException {

		Map<String, Object> values = createVParams(iTableBean, clazz);

		try {
			String vm = "template/java/IAopService.java.vm";

			String aopfileName = "Aop" + clazz.getSimpleName().substring(0, clazz.getSimpleName().length() - 4)
					+ "Service.java";
			VelocityToolsUnits.toFile(vm, values, outPath + "\\controller\\aop\\core\\" + "I" + aopfileName);

		} catch (Exception e) {
			throw new MyBatisBuildServiceException(e);
		}

		try {
			String vm = "template/java/AopService.java.vm";

			String aopfileName = "Aop" + clazz.getSimpleName().substring(0, clazz.getSimpleName().length() - 4)
					+ "Service.java";

			VelocityToolsUnits.toFile(vm, values, outPath + "\\controller\\aop\\core\\imples\\" + aopfileName);
		} catch (Exception e) {
			throw new MyBatisBuildServiceException(e);
		}

		try {
			String vm = "template/java/AopExBaseService.java.vm";

			String aopfileName = "Aop" + clazz.getSimpleName().substring(0, clazz.getSimpleName().length() - 4)
					+ "ExBaseService.java";

			VelocityToolsUnits.toFile(vm, values, outPath + "\\controller\\aop\\core\\imples\\" + aopfileName);
		} catch (Exception e) {
			throw new MyBatisBuildServiceException(e);
		}

		try {
			String vm = "template/java/IAopExService.java.vm";

			String aopfileName = "Aop" + clazz.getSimpleName().substring(0, clazz.getSimpleName().length() - 4)
					+ "ExService.java";
			VelocityToolsUnits.toFile(vm, values, outPath + "\\controller\\aop\\" + "I" + aopfileName);

		} catch (Exception e) {
			throw new MyBatisBuildServiceException(e);
		}
		
		try {
			String vm = "template/java/AopExService.java.vm";

			String aopfileName = "Aop" + clazz.getSimpleName().substring(0, clazz.getSimpleName().length() - 4)
					+ "ExService.java";

			VelocityToolsUnits.toFile(vm, values, outPath + "\\controller\\aop\\" + aopfileName);
		} catch (Exception e) {
			throw new MyBatisBuildServiceException(e);
		}

	}

	protected void buildAction(TableBean iTableBean, Class<?> clazz, String outPath)
			throws MyBatisBuildServiceException {

		// if (StringUtils.isAllEmpty(iTableBean.getAction())) {
		// System.out.println(">>> TableBean action is null ! no build
		// action java flie ");
		// }

		Map<String, Object> values = createVParams(iTableBean, clazz);

		try {
			String vm = "template/java/Action.java.vm";

			String aopfileName = clazz.getSimpleName() + "Controller.java";
			VelocityToolsUnits.toFile(vm, values, outPath + "\\controller\\" + aopfileName);

		} catch (Exception e) {
			throw new MyBatisBuildServiceException(e);
		}

		
	}

	protected void buildService(TableBean iTableBean, Class<?> clazz, String outPath)
			throws MyBatisBuildServiceException {

		Map<String, Object> values = createVParams(iTableBean, clazz);

		
		
		
		
		try {
			String vm = "template/java/IBaseServiceImple.java.vm";

			String aopfileName = clazz.getSimpleName().substring(0, clazz.getSimpleName().length() - 4) + "BaseServiceImple.java";

			VelocityToolsUnits.toFile(vm, values, outPath + "\\service\\imples\\base\\" + aopfileName);
		} catch (Exception e) {
			throw new MyBatisBuildServiceException(e);
		}
		
		try {
			String vm = "template/java/IService.java.vm";

			String aopfileName = clazz.getSimpleName().substring(0, clazz.getSimpleName().length() - 4) + "Service.java";
			VelocityToolsUnits.toFile(vm, values, outPath + "\\service\\" + "I" + aopfileName);

		} catch (Exception e) {
			throw new MyBatisBuildServiceException(e);
		}

		try {
			String vm = "template/java/IServiceImple.java.vm";

			String aopfileName = clazz.getSimpleName().substring(0, clazz.getSimpleName().length() - 4) + "ServiceImple.java";

			VelocityToolsUnits.toFile(vm, values, outPath + "\\service\\imples\\" + aopfileName);
		} catch (Exception e) {
			throw new MyBatisBuildServiceException(e);
		}
	}

	protected void buildSaveUpdateUnits(TableBean iTableBean, Class<?> clazz, String outPath)
			throws MyBatisBuildServiceException {

		Map<String, Object> values = createVParams(iTableBean, clazz);

		String vm = "template/java/SaveUpdateUnits.java.vm";

		try {
			VelocityToolsUnits.toFile(vm, values,
					outPath + "\\service\\units\\" + clazz.getSimpleName().substring(0, clazz.getSimpleName().length() - 4) + "SaveUpdateRemoveUnits.java");
		} catch (Exception e) {
			throw new MyBatisBuildServiceException(e);
		}
	}

	protected void buildSearchUnits(TableBean iTableBean, Class<?> clazz, String outPath)
			throws MyBatisBuildServiceException {

		Map<String, Object> values = createVParams(iTableBean, clazz);

		String vm = "template/java/SearchUnits.java.vm";

		try {
			VelocityToolsUnits.toFile(vm, values,
					outPath + "\\service\\units\\" + clazz.getSimpleName().substring(0, clazz.getSimpleName().length() - 4) + "SearchUnits.java");
		} catch (Exception e) {
			throw new MyBatisBuildServiceException(e);
		}
	}

	protected void buildCrate(TableBean iTableBean, Class<?> clazz, DBUtils dbutils, String vmfile)
			throws MyBatisBuildServiceException {

		Map<String, Object> values = createVParams(iTableBean, clazz);

		try {
			VelocityToolsUnits.toDB(vmfile, values, dbutils);
			System.out.println("create table : " + iTableBean.getName() + " success ! ");
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyBatisBuildServiceException(e);
		}
	}

	protected void buildCrate(TableBean iTableBean, Class<?> clazz, DBUtils dbutils)
			throws MyBatisBuildServiceException {

		String vm = "template/java/CreateTable.sql.vm";
		buildCrate(iTableBean, clazz, dbutils, vm);
	}

}
