package cn.oxo.iworks.web.controller.bind.ongl;

@java.lang.annotation.Target(value = { java.lang.annotation.ElementType.FIELD })
@java.lang.annotation.Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@java.lang.annotation.Inherited
@java.lang.annotation.Documented
public abstract @interface MYformat {
      public String format() default "";

      public String verification() default "";
}
