/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package cn.oxo.iworks.web.controller.bind.ongl;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.time.DateUtils;

import ognl.ObjectPropertyAccessor;
import ognl.OgnlException;

public class MYObjectAccessor extends ObjectPropertyAccessor {
	@SuppressWarnings("unchecked")
	@Override
	public Object getProperty(@SuppressWarnings("rawtypes") Map context, Object target, Object oname)
			throws OgnlException {
		if (oname instanceof String) {
			try {
				String field = (String) oname;
				Field iField = FieldUtils.getField(target.getClass(), field, true);
				if (iField == null)
					throw new OgnlException("Field " + oname + " not   not find ! ");
				Object object = iField.get(target);
				if (object == null) {
					if (iField.getType().equals(List.class)) {
						object = ArrayList.class.newInstance();
						iField.set(target, object);
						Class<?> genericClazz = OnGLUnits.getFieldGenericType(iField);
						context.put("ListType", genericClazz);
					} else {
						object = iField.getType().newInstance();
						iField.set(target, object);
					}
				}
				return object;
			} catch (Exception e) {
				throw new OgnlException(e.getMessage(), e);
			}
		} else {
			throw new OgnlException("oname " + oname + " not String  not work ! ");
		}

	}
	
	//正则表达式通用匹配
    private static boolean genericMatcher(String regexExpre,String testStr){
        Pattern pattern=Pattern.compile(regexExpre);
        Matcher matcher = pattern.matcher(testStr);
        return matcher.matches();
    }

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void setProperty(Map context, Object target, Object oname, Object value) throws OgnlException {
		Field iField = FieldUtils.getField(target.getClass(), (String) oname, true);
		if (iField == null)
			throw new OgnlException("not find field name " + ((String) oname) + " field ! ");
		if(iField.isAnnotationPresent(MYformat.class) && value !=null) {
			MYformat iMYformat = iField.getAnnotation(MYformat.class);
			if(!iMYformat.verification().equals("")) {
			//验证 正则验证
		     if(!genericMatcher(iMYformat.verification(),value.toString())){
		    	 throw new OgnlException(" iField  "+iField.getName()+" value "+value+" format error !"); 
		     }
			}
		}
		if (OnGLUnits.verificationBaseValue(iField.getType())) {
			if (iField.getType().equals(Date.class)) {
				if (value instanceof String) {
					if (iField.isAnnotationPresent(MYformat.class)) {
						MYformat iMYformat = iField.getAnnotation(MYformat.class);
						try {
							super.setProperty(context, target, oname,
									DateUtils.parseDate((String) value, iMYformat.format()));
						} catch (ParseException e) {
							throw new OgnlException(e.getMessage(), e);
						}
					} else {
						super.setProperty(context, target, oname, value);

					}

				} else {
					super.setProperty(context, target, oname, value);
				}

			} else {
				if (value == null || value.toString().equals("")) {
					super.setProperty(context, target, oname, null);
				} else {
					super.setProperty(context, target, oname, value);
				}

				// if (iField.getType().equals(String.class)) {
				// if (value == null || value.toString().equals("")) {
				// super.setProperty(context, target, oname, null);
				// } else {
				// super.setProperty(context, target, oname, value);
				// }
				// }else {
				// super.setProperty(context, target, oname, value);
				// }

				//
			}

		} else

		if (OnGLUnits.verificationEnumValue(iField.getType())) {
			if(value!=null && !StringUtils.isEmpty( value.toString())) {
			   Object x =	Enum.valueOf(((Class)iField.getType()), value.toString());
				super.setProperty(context, target, oname,  x);
			}
			
		} else

		{
			try {
				// LIST
				if (!OnGLUnits.verificationListValue(iField.getType()))
					throw new OgnlException("Field : " + iField.getType() + "  not support !  list  ");

				List object = (List) iField.get(target);
				if (object == null) {
					object = new ArrayList<>();
					iField.set(target, object);
				}
				Class<?> genericClazz = OnGLUnits.getFieldGenericType(iField);
				if (value instanceof List) {
					for (Object v : (List<Object>) value) {
						object.add(OnGLUnits.switchObjects(v, genericClazz));
					}
				} else {
					object.add(OnGLUnits.switchObjects(value, genericClazz));
				}

			} catch (IllegalArgumentException | IllegalAccessException | OnGLUnitsException e) {
				throw new OgnlException(e.getMessage(), e);
			}

		}
	}

}