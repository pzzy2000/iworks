package cn.oxo.iworks.builds.html;

/**
 * 定义模块名
 * 
 * @author 1
 *
 */
@java.lang.annotation.Target(value = { java.lang.annotation.ElementType.TYPE })
@java.lang.annotation.Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
public abstract @interface ModuleDif {

  
    public abstract java.lang.String id();

    public abstract String name() default "";

    public abstract String action();

    public Opt[] opt() default { Opt.add, Opt.delete, Opt.update, Opt.search };

}
