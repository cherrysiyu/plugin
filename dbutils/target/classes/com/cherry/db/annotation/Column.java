package com.cherry.db.annotation;



import static java.lang.annotation.ElementType.FIELD;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Column
{
	/**
	 * 指定对应的数据库列名
	 * <br>如果为空，则默认采用被注解的属性名
	 */
	public abstract String name() default "";
	
	/**
	 * 数据库列别名，常用于在自动生成SELECT语句时用于AS使用
	 */
	public abstract String alias() default "";
	
	// 是否非空
	//public abstract boolean notNull() default false;
	
	/**
	 * 标识是否主键
	 * @deprecated 使用 {@link #primaryKey()} 替代
	 */
	@Deprecated
	public abstract boolean isPrimaryKey() default false;
	
	/**
	 * 标识是否是主键，默认值：false
	 */
	public abstract boolean primaryKey() default false;
	
	/**
	 * 列值生成方式，默认值： GeneratorType.ASSIGN
	 * <br>详见：{@link GeneratorType}
	 */
	public abstract GeneratorType generatorType() default GeneratorType.ASSIGN;
	
	/**
	 * SEQUENCE名称，用于 Oracle数据库的主键定义
	 */
	public abstract String sequence() default "";
	
}
