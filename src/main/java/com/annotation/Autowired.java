package com.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/*
* 自动装配
* */


@Retention(RUNTIME)// 该注解在运行时可以被读取和使用
@Target(FIELD)// 该注解用于描述域
public @interface Autowired {
}
