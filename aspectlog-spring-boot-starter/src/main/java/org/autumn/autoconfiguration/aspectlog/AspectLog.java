package org.autumn.autoconfiguration.aspectlog;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义AspectLog注解，该注解用于标注需要打印执行时间的方法
 * @author : SunLin
 * @date : 2020/8/19
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AspectLog {
}
