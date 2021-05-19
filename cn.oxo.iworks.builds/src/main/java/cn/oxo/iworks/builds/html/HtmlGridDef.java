package cn.oxo.iworks.builds.html;

import cn.oxo.iworks.builds.html.FieldParams.FieldShow;
import cn.oxo.iworks.builds.html.FieldParams.FieldType;

/**
 * 主要定义EXTJS grid from 的 页面代码
 * 
 * @author you
 *
 */
@java.lang.annotation.Target(value = { java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.FIELD })
@java.lang.annotation.Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
public abstract @interface HtmlGridDef {

      /**
       * grid width
       * 
       * @return
       */
      public int width() default 200;

      // /**
      // * grid isShow
      // * @return
      // */
      public boolean isShow() default true;

      /**
       * 是否能为空
       * 
       * @return
       */
      public boolean isNull() default false;

      /**
       * 描述说明
       * 
       * @return
       */
      public String desc() default "";

      public String validate() default "";

      /**
       * 字段名称
       * 
       * @return
       */
      public abstract String name() default "";

      /**
       * 字段数据映射
       * 
       * @return
       */
      public String mapping() default "";

      /**
       * 是否可以排序
       * 
       * @return
       */
      public boolean isSort() default false;

      /**
       * 字段类型
       * 
       * @return
       */
      public FieldType fieldType() default FieldType.text;

      /**
       * 默认排序顺序
       * 
       * @return
       */
      public int order() default -100;

      public FieldShow[] fieldShow() default { FieldShow.Grid, FieldShow.Search, FieldShow.Input };

}
