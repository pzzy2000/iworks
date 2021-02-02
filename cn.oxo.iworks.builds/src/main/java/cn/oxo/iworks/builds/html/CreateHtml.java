package cn.oxo.iworks.builds.html;

import java.util.HashMap;
import java.util.Map;

import cn.oxo.iworks.builds.VelocityToolsUnits;

public class CreateHtml {

	private String basePath;

	public void create(Class<?> clazz, String basePath) throws Exception {

		ScanHtmlTableBean iScanHtmlTableBean = new ScanHtmlTableBean();

		ModuleDifBean iModuleDifBean = iScanHtmlTableBean.scan(clazz);

		this.basePath = basePath;

		createModuleHtml(iModuleDifBean, clazz);

		createModuleJs(iModuleDifBean, clazz);

		createModuleadd(iModuleDifBean, clazz);

		createModuledelete(iModuleDifBean, clazz);

		createModulesearch(iModuleDifBean, clazz);

		createModuleUpdate(iModuleDifBean, clazz);
	}

	private Map<String, Object> createVParams(ModuleDifBean iModuleDifBean, Class<?> clazz) {
		Map<String, Object> values = new HashMap<String, Object>();

		values.put("ModuleDifBean", iModuleDifBean);

		return values;
	}

	private void createModuleadd(ModuleDifBean iModuleDifBean, Class<?> clazz) throws Exception {
		System.out.println("->  create html : " + basePath + "/html/default/module/"
				+ iModuleDifBean.getModuleDif().id() + ".html");
		Map<String, Object> values = createVParams(iModuleDifBean, clazz);

		String vm = "template/html/add.html";

		VelocityToolsUnits.toFile(vm, values,
				basePath + "/html/default/module/" + iModuleDifBean.getModuleDif().id() + "_" + "add.html");

	}

	private void createModuledelete(ModuleDifBean iModuleDifBean, Class<?> clazz) throws Exception {
		System.out.println("->  create html : " + basePath + "/html/default/module/"
				+ iModuleDifBean.getModuleDif().id() + ".html");
		Map<String, Object> values = createVParams(iModuleDifBean, clazz);

		String vm = "template/html/delete.html";

		VelocityToolsUnits.toFile(vm, values,
				basePath + "/html/default/module/" + iModuleDifBean.getModuleDif().id() + "_" + "delete.html");

	}

	private void createModulesearch(ModuleDifBean iModuleDifBean, Class<?> clazz) throws Exception {
		System.out.println("->  create html : " + basePath + "/html/default/module/"
				+ iModuleDifBean.getModuleDif().id() + ".html");
		Map<String, Object> values = createVParams(iModuleDifBean, clazz);

		String vm = "template/html/search.html";

		VelocityToolsUnits.toFile(vm, values,
				basePath + "/html/default/module/" + iModuleDifBean.getModuleDif().id() + "_" + "search.html");

	}

	private void createModuleUpdate(ModuleDifBean iModuleDifBean, Class<?> clazz) throws Exception {
		System.out.println("->  create html : " + basePath + "/html/default/module/"
				+ iModuleDifBean.getModuleDif().id() + ".html");
		Map<String, Object> values = createVParams(iModuleDifBean, clazz);

		String vm = "template/html/update.html";

		VelocityToolsUnits.toFile(vm, values,
				basePath + "/html/default/module/" + iModuleDifBean.getModuleDif().id() + "_" + "update.html");

	}

	private void createModuleHtml(ModuleDifBean iModuleDifBean, Class<?> clazz) throws Exception {
		System.out.println("->  create html : " + basePath + "/html/default/module/"
				+ iModuleDifBean.getModuleDif().id() + ".html");
		Map<String, Object> values = createVParams(iModuleDifBean, clazz);

		String vm = "template/html/module.html.vm";

		VelocityToolsUnits.toFile(vm, values,
				basePath + "/html/default/module/" + iModuleDifBean.getModuleDif().id() + ".html");

	}

	private void createModuleJs(ModuleDifBean iModuleDifBean, Class<?> clazz) throws Exception {
		System.out.println("->  create html : " + iModuleDifBean.getModuleDif().id() + ".html  ? "
				+ iModuleDifBean.getModuleDif().id() + "/MainOpt.js");
		Map<String, Object> values = createVParams(iModuleDifBean, clazz);

		String vm = "template/html/MainOpt.js";

		VelocityToolsUnits.toFile(vm, values, basePath + "/html/default/module/" + iModuleDifBean.getModuleDif().id()
				+ "/" + iModuleDifBean.getModuleDif().id() + "opt.js");

	}

}
