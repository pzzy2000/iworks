package cn.oxo.iworks.web.controller.bind;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.oxo.iworks.web.controller.bind.annotation.AllowParams;
import cn.oxo.iworks.web.controller.bind.annotation.AllowParams.Type;
import cn.oxo.iworks.web.controller.bind.annotation.FormModel;
import cn.oxo.iworks.web.controller.bind.ongl.MYListPropertyAccessor;
import cn.oxo.iworks.web.controller.bind.ongl.MYObjectAccessor;
import cn.oxo.iworks.web.controller.bind.ongl.OnGLUnits;
import cn.oxo.iworks.web.controller.bind.ongl.OnGLUnitsException;
import ognl.OgnlRuntime;;

public class MYBindBeanHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

	private static Logger logger = LogManager.getLogger(MYBindBeanHandlerMethodArgumentResolver.class);

	private Map<String, AllowBeanParams> allowParams = new ConcurrentHashMap<String, AllowBeanParams>();

	private IVerificationfilter verificationfilter;

	public MYBindBeanHandlerMethodArgumentResolver(IVerificationfilter verificationfilter) {
		this();
		this.verificationfilter = verificationfilter;

	}

	public MYBindBeanHandlerMethodArgumentResolver() {
		OgnlRuntime.setPropertyAccessor(Object.class, new MYObjectAccessor());

		OgnlRuntime.setPropertyAccessor(List.class, new MYListPropertyAccessor());
	}

	class AllowBeanParams {
		private Type type;

		// bean
		private Map<String, List<String>> fieldMap = new HashMap<String, List<String>>();

		public AllowBeanParams(Type type, String[] fields_) {
			super();
			this.type = type;
			// this.field = field;
			if (fields_ == null)
				return;
			for (String fields : fields_) {
				int i = fields.indexOf(":");
				String bean = fields.substring(0, i);
				List<String> field = Arrays.asList(fields.substring(i + 1).split(","));
				fieldMap.put(bean, field);
			}

		}

		public Type getType() {
			return type;
		}

		public Map<String, List<String>> getFieldMap() {
			return fieldMap;
		}

	}

	private AllowBeanParams getAllowParams(Method iMethod) {
		String key = iMethod.getDeclaringClass().getName() + "." + iMethod.getName();
		AllowBeanParams iAllowParams = allowParams.get(key);
		if (iAllowParams == null) {
			synchronized (key) {
				if (iMethod.isAnnotationPresent(AllowParams.class)) {
					AllowParams iAllow_Params = iMethod.getAnnotation(AllowParams.class);
					allowParams.put(key, new AllowBeanParams(iAllow_Params.type(), iAllow_Params.field()));
					return allowParams.get(key);
				} else {
					allowParams.put(key, new AllowBeanParams(Type.none, null));
				}
				return allowParams.get(key);
			}
		} else {
			return iAllowParams;
		}
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer arg1, NativeWebRequest webRequest,WebDataBinderFactory arg3) throws Exception {
		
		FormModel iFormModel = parameter.getParameterAnnotation(FormModel.class);

		Object result = null;

		if (iFormModel.parameterName().equals(FormModel.parameter_null)) {

			Object iObject = createNullRequestBean(webRequest, iFormModel.parameterName(),
					parameter.getParameterType());
			result = iObject;
		} else

		// 普通格式
		if (iFormModel.format().equals(FormModel.Format.defaults)) {
			Method iMethod = parameter.getMethod();
			AllowBeanParams iAllowParams = getAllowParams(iMethod);
			Object iObject = createRequestBean(webRequest, iFormModel.parameterName(), parameter.getParameterType(),
					iAllowParams);

			result = iObject;

		} else {
			// json 格式
			result = createRequestJsonBean(webRequest, iFormModel.parameterName(), parameter.getParameterType());
		}

//		verificationfilter.verification(iFormModel.verification(), result);

		return result;

	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(FormModel.class);

	}

	@SuppressWarnings("unchecked")
	protected <V> V createRequestJsonBean(NativeWebRequest request, String identifier, Class<V> clazz)
			throws Exception {

		String re = request.getParameter(identifier);
		JSONObject iJSONObject = JSON.parseObject(re);
		return iJSONObject.toJavaObject(clazz);

	}

	protected <V> V createNullRequestBean(NativeWebRequest request, String identifier, Class<V> clazz)
			throws Exception {

		V v = clazz.newInstance();
		for (Entry<String, String[]> parameter : request.getParameterMap().entrySet()) {
			try {
				{
					String expression = parameter.getKey();
					if (parameter.getValue().length == 0)
						continue;

					OnGLUnits.setPropertys(v, expression, parameter.getValue().length == 1 ? parameter.getValue()[0]
							: java.util.Arrays.asList(parameter.getValue()));

				}
			} catch (OnGLUnitsException e) {
				logger.error(e.getMessage());
				// throw new Exception(e);
			}
		}

		return v;

	}

	protected <V> V createRequestBean(NativeWebRequest request, String identifier, Class<V> clazz,
			AllowBeanParams iAllowParams) throws Exception {

		V v = clazz.newInstance();

		for (Entry<String, String[]> parameter : request.getParameterMap().entrySet()) {
			try {
				if (parameter.getKey().startsWith(identifier + ".")) {
					String bean = parameter.getKey().substring(0, parameter.getKey().indexOf("."));
					String expression = parameter.getKey().substring(parameter.getKey().indexOf(".") + 1);
					if (parameter.getValue().length == 0)
						continue;
					if (parameter.getValue().length == 1
							&& (parameter.getValue()[0] == null || parameter.getValue()[0].equals("")))
						continue;

					if (expression.toLowerCase().endsWith("ids_")) {
						expression = expression.substring(0, expression.length() - 1);
					}

					// UI
					if (iAllowParams.getType().equals(Type.none)) {
						OnGLUnits.setPropertys(v, expression, values(parameter.getKey(), parameter.getValue()));

					} else {
						// 1: 包含 ;0:排除
						Type type = iAllowParams.getType();

						if (!iAllowParams.getFieldMap().containsKey(bean)) {
							OnGLUnits.setPropertys(v, expression, values(parameter.getKey(), parameter.getValue()));
						} else {
							if (type.equals(Type.include)) {
								if (iAllowParams.getFieldMap().get(bean).contains(expression)) {
									OnGLUnits.setPropertys(v, expression,
											values(parameter.getKey(), parameter.getValue()));
								}
							} else {
								if (!iAllowParams.getFieldMap().get(bean).contains(expression)) {
									OnGLUnits.setPropertys(v, expression,
											values(parameter.getKey(), parameter.getValue()));
								}
							}
						}

					}

					//

				} else {
					continue;
				}
			} catch (Exception e) {
				logger.error(logger, e);
				throw new Exception(e);
			}
		}

		return v;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Object values(String key, String[] value) {
		if (key.toLowerCase().endsWith("ids_")) {
			return Arrays.asList(value[0].split(","));
		} else {
			return value.length == 1 ? value[0] : Arrays.asList(value);
		}

	}
}
