package cn.oxo.iworks.builds.html;

@java.lang.annotation.Target(value = { java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.FIELD })
@java.lang.annotation.Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
public abstract @interface LocalCombos {

      // {"key:value","key:value"}
      public abstract String[] value();

      // public abstract LocalCombo[] value() ;
}
