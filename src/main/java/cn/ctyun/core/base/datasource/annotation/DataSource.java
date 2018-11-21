package cn.ctyun.core.base.datasource.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 切换数据源注解 可以用于类或者方法级别 优先级：方法 > 类<br/>
 * 默认为 master
 * 
 * @author kaibyx
 * @date 2018年11月19日 下午5:37:16
 */
@Target({ ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {

    /**
     * 
     * @return
     */
    public String value() default "master"; // 该值即key值

}
