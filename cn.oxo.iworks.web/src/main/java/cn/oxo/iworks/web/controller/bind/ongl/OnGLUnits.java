package cn.oxo.iworks.web.controller.bind.ongl;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;

import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlException;

public class OnGLUnits {

      public static void main(String[] args) {
            System.out.println(Integer.valueOf("12"));
      }

      // static {
      // OgnlRuntime.setPropertyAccessor(Object.class, new MYObjectAccessor());
      //
      // OgnlRuntime.setPropertyAccessor(List.class, new
      // MYListPropertyAccessor());
      // }

      // boolean, byte, char, short, int, long, float, 和double

      public static boolean verificationBaseValue(Class<?> value) {
            if (value.isPrimitive())
                  return true;
            else if (value.equals(String.class))
                  return true;
            else if (value.equals(Boolean.class))
                  return true;
            else if (value.equals(Byte.class))
                  return true;
            else if (value.equals(Character.class))
                  return true;
            else if (value.equals(Short.class))
                  return true;
            else if (value.equals(Integer.class))
                  return true;
            else if (value.equals(Long.class))
                  return true;
            else if (value.equals(Float.class))
                  return true;
            else if (value.equals(Double.class))
                  return true;
            if (value.equals(Date.class))
                  return true;

            else if (value.equals(BigDecimal.class))
                  return true;

            return false;
      }

      @SuppressWarnings("rawtypes")
      public static Object switchObjects(Object value, Class clazz) throws OnGLUnitsException {
            if (value == null)
                  return null;
            if (value instanceof String) {
                  String value_ = value.toString();
                  if (value_ == null || value_.equals(""))
                        return null;
                  if (clazz.equals(String.class)) {
                        return clazz.cast(value);
                  } else if (clazz.equals(Boolean.class)) {
                        return Boolean.valueOf(value_);
                  } else if (clazz.equals(Byte.class)) {
                        return Byte.valueOf(value_);
                  } else if (clazz.equals(Character.class)) {
                        throw new OnGLUnitsException("Character  type  not  support ! ");
                  } else if (clazz.equals(Short.class)) {
                        return Short.valueOf(value_);
                  } else if (clazz.equals(Integer.class)) {
                        return Integer.valueOf(value_);
                  } else if (clazz.equals(Long.class)) {
                        return Long.valueOf(value_);
                  } else if (clazz.equals(Float.class)) {
                        return Float.valueOf(value_);
                  } else if (clazz.equals(Double.class)) {
                        return Double.valueOf(value_);
                  } else if (clazz.equals(Date.class)) {
                        try {
                              return DateUtils.parseDate(value_, "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss");
                        } catch (Exception e) {
                              throw new OnGLUnitsException("clazz : " + clazz + "   type  not  support ! ", e);
                        }
                  } else {
                        throw new OnGLUnitsException("clazz : " + clazz + "   type  not  support ! ");
                  }

            } else {
                  return clazz.cast(value);
            }

      }

      public static boolean verificationEnumValue(Class<?> value) {

            if (value.isEnum())
                  return true;

            return false;

      }

      public static boolean verificationListValue(Class<?> value) {

            if (value.equals(List.class))
                  return true;

            return false;

      }

      private static boolean verification(Object value) {
            if (verificationBaseValue(value) || verificationListValue(value))
                  return true;
            return false;

      }

      private static boolean verificationBaseValue(Object value) {
            if (value.getClass().isPrimitive())
                  return true;
            if (value instanceof String)
                  return true;
            if (value instanceof Boolean)
                  return true;
            if (value instanceof Byte)
                  return true;
            if (value instanceof Byte)
                  return true;
            if (value instanceof Short)
                  return true;
            if (value instanceof Integer)
                  return true;
            if (value instanceof Long)
                  return true;
            if (value instanceof Float)
                  return true;
            if (value instanceof Double)
                  return true;

            if (value instanceof Date)
                  return true;
            return false;

      }

      private static boolean verificationListValue(Object value) {
            if (value instanceof List)
                  return true;
            return false;

      }

      public static <V> void setPropertys(V object, String expression, Object value) throws OnGLUnitsException {

            if (verification(value)) {

            } else {
                  throw new OnGLUnitsException("value class type : " + value.getClass().getName() + "  not support ! ");
            }

            try {
                  OgnlContext context = (OgnlContext) Ognl.createDefaultContext(null, new DefaultMemberAccess(true));
                  if (expression.equals("pageSize"))
                        value = 10;
                  Ognl.setValue(expression, context, object, value);
            } catch (OgnlException e) {
                  throw new OnGLUnitsException(e);
            }
      }

      public static Class<?> getFieldGenericType(Field iField) throws OnGLUnitsException {
            ParameterizedType listGenericType = (ParameterizedType) iField.getGenericType();
            if (listGenericType.getActualTypeArguments().length > 1) {
                  throw new OnGLUnitsException("Field " + iField.getName() + "  ParameterizedType length > 1 ; not support ! ");
            }
            Class<?> genericClazz = (Class<?>) listGenericType.getActualTypeArguments()[0];
            return genericClazz;
      }

}
