package cn.oxo.iworks.builds.base;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.reflect.FieldUtils;

import cn.oxo.iworks.databases.annotation.Column;
import cn.oxo.iworks.databases.annotation.ColumnType;
import cn.oxo.iworks.databases.annotation.Id;
import cn.oxo.iworks.databases.annotation.Index;
import cn.oxo.iworks.databases.annotation.SelectType;
import cn.oxo.iworks.databases.annotation.Table;

public class ScanTableBean {

      public TableBean scan(Class<?> clazz) throws MyBatisBuildServiceException {

            TableBean iTableBean = new TableBean();

            paserTable(iTableBean, clazz);

            paserColumn(iTableBean, clazz);

            return iTableBean;

      }

      private void paserColumn(TableBean iTableBean, Class<?> clazz) throws MyBatisBuildServiceException {

            iTableBean.setPojo(clazz);

            List<ColumnBean> columnBeans = new ArrayList<ColumnBean>();

            iTableBean.setColumnBeans(columnBeans);

            Field[] fields = FieldUtils.getAllFields(clazz);

            for (Field field : fields) {

                  if (field.isAnnotationPresent(Column.class)) {

                        Column iColumn = field.getAnnotation(Column.class);

                        ColumnBean iColumnBean = new ColumnBean();

                        iColumnBean.setColumnType(iColumn.columnType());

                        iColumnBean.setLength(iColumn.length());

                        iColumnBean.setScale(iColumn.scale());

                        iColumnBean.setName(iColumn.name());

                        iColumnBean.setDesc(iColumn.desc());

                        iColumnBean.setDefaultValue(iColumn.defaultValue());

                        if (field.getType().equals(String.class)) {
                              iColumnBean.setSelectType(iColumn.selectType());
                        } else {
                              iColumnBean.setSelectType(SelectType.EQ);
                        }

                        iColumnBean.setFieldType(field.getType());

                        iColumnBean.setFieldName(field.getName());

                        iColumnBean.setCanNull(iColumn.isCanNull());

                        if (field.isAnnotationPresent(Index.class)) {
                              iColumnBean.setIndex(field.getAnnotation(Index.class));
                        }

                        haveLength(iColumnBean);

                        System.out.println("-> " + iColumnBean.toString());

                        if (field.isAnnotationPresent(Id.class)) {
                              iTableBean.setIdColumn(iColumnBean);
                        } else {
                              columnBeans.add(iColumnBean);
                        }

                  }

            }
      }

      private void haveLength(ColumnBean iColumnBean) {
            ColumnType columnType = iColumnBean.getColumnType();
            if (columnType.equals(ColumnType.DATE) || columnType.equals(ColumnType.TIMESTAMP) || columnType.equals(ColumnType.TIME)) {
                  iColumnBean.setLength(-1);
            } else {
                  // return iColumnBean.getLength();
            }
      }

      private void paserTable(TableBean iTableBean, Class<?> clazz) throws MyBatisBuildServiceException {

            if (clazz.isAnnotationPresent(Table.class)) {

                  Table iTable = clazz.getAnnotation(Table.class);

                  iTableBean.setName(iTable.name());
                  iTableBean.setAction(iTable.action());
                  iTableBean.setDesc(iTable.desc());

            } else {
                  throw new MyBatisBuildServiceException(clazz.getName() + " not find  @Table");
            }

      }

}
