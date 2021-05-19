package cn.oxo.iworks.web.controller.bind.ongl;

import java.util.List;
import java.util.Map;

import ognl.ListPropertyAccessor;
import ognl.NoSuchPropertyException;
import ognl.OgnlException;
import ognl.OgnlRuntime;

public class MYListPropertyAccessor extends ListPropertyAccessor {

      @SuppressWarnings("rawtypes")
      @Override
      public Object getProperty(Map context, Object target, Object name) throws OgnlException {
            return super.getProperty(context, target, name);
      }

      @SuppressWarnings({ "unchecked", "rawtypes" })
      @Override
      public void setProperty(Map context, Object target, Object name, Object value) throws OgnlException {

            List xxx = (List) target;
            Class<?> clazz = (Class<?>) context.get("ListType");
            for (Object v : (List<Object>) value) {
                  try {
                        if (OnGLUnits.verificationBaseValue(clazz)) {
                              xxx.add(OnGLUnits.switchObjects(v, clazz));
                        } else if (OnGLUnits.verificationListValue(clazz)) {
                              Object newObject = clazz.newInstance();
                              setPrivateProperty(context, newObject, name, v);
                              xxx.add(newObject);
                        } else {
                              throw new OnGLUnitsException("clazz : " + clazz + "   type  not  support ! ");
                        }
                  } catch (InstantiationException | IllegalAccessException | OnGLUnitsException e) {
                        throw new OgnlException(e.getMessage(), e);

                  }
            }

      }

      @SuppressWarnings("rawtypes")
      private void setPrivateProperty(Map context, Object target, Object oname, Object value) throws OgnlException {
            String name = oname.toString();

            Object result = setPossibleProperty(context, target, name, value);

            if (result == OgnlRuntime.NotFound) {
                  throw new NoSuchPropertyException(target, name);
            }
      }

}
