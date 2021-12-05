package springIoc.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


/*
* 标注Controller类
* */
@Target(TYPE)
@Retention(RUNTIME)
public @interface Controller {
    String value() default "";// 映射的地址
}
