package myioc.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)// 该注解在运行时可以被读取和使用
@Target(TYPE)// 描述类型
public @interface Scope {
}
