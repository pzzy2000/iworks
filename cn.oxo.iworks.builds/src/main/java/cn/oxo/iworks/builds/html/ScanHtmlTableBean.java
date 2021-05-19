package cn.oxo.iworks.builds.html;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.reflect.FieldUtils;

import cn.oxo.iworks.builds.html.FieldParams.FieldType;

public class ScanHtmlTableBean {

      public ModuleDifBean scan(Class<?> clazz) throws Exception {

            ModuleDifBean iTableBean = new ModuleDifBean();

            paserTable(iTableBean, clazz);

            paserColumn(iTableBean, clazz);

            return iTableBean;

      }

      private void paserTable(ModuleDifBean iTableBean, Class<?> clazz) throws Exception {
            if (clazz.isAnnotationPresent(ModuleDif.class)) {

                  ModuleDif iTable = clazz.getAnnotation(ModuleDif.class);
                  iTableBean.setModuleDif(iTable);

            } else {
                  System.err.println("not find ModuleDif -> class : " + clazz.getName());
                  throw new Exception("not find ModuleDif -> class : " + clazz.getName());
            }
      }

      private void paserColumn(ModuleDifBean iTableBean, Class<?> clazz) throws Exception {

            Field[] fields = FieldUtils.getAllFields(clazz);
            for (Field field : fields) {

                  if (!field.isAnnotationPresent(HtmlGridDef.class))
                        continue;

                  HtmlGridDef iColumn = field.getAnnotation(HtmlGridDef.class);

                  HtmlGridDefBean iHtmlGridDefBean = new HtmlGridDefBean();
                  iTableBean.getHtmlGridDefBeans().add(iHtmlGridDefBean);

                  iHtmlGridDefBean.setHtmlGridDef(iColumn);

                  iHtmlGridDefBean.setName(field.getName());

                  paserLocalCombo(iHtmlGridDefBean, field);

            }

      }

      // "k:v";"k:v""k:v"
      private void paserLocalCombo(HtmlGridDefBean iHtmlGridDefBean, Field field) throws Exception {

            if (!iHtmlGridDefBean.getHtmlGridDef().fieldType().equals(FieldType.localCombo))
                  return;

            if (!field.isAnnotationPresent(LocalCombos.class)) {

                  System.out.println(" FieldType :  " + FieldType.localCombo + " not have @LocalCombos");

                  throw new Exception(" FieldType :  " + FieldType.localCombo + " not have @LocalCombos");
            }

            LocalCombos iLocalCombos = field.getAnnotation(LocalCombos.class);

            List<KValue> kvalues = new ArrayList<KValue>();

            String[] vs = iLocalCombos.value();

            for (String v : vs) {
                  String[] v_ = v.split(":");
                  KValue iKValue = new KValue();
                  iKValue.setKey(v_[0]);
                  iKValue.setValue(v_[1]);
                  kvalues.add(iKValue);
            }
            iHtmlGridDefBean.setDetail(kvalues);
      }

}
