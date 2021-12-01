package myioc;

/*
* ioc容器
* */

import myioc.annotation.*;
import myioc.utils.ClassUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/*
* ioc容器
* 映射关系为<类，类实体>，因此scope默认为单例
* */

public class ApplicationContext {
    private Map<Class<?>, Object> map = new HashMap<>();// 类到实体的映射

    /*
    * 传入配置文件进行注入
    * */
    public ApplicationContext(Class<?>... clss){
        for(Class<?> cls: clss){
            loadBeans(cls);// 注入bean
            scanComponents(cls);// 扫描指定路径注入组件
        }
    }

    /*
     * @ComponentScan
     * 扫描指定路径注入组件
     * */
    private void scanComponents(Class<?> cls){
        ComponentScan componentScan = cls.getAnnotation(ComponentScan.class);
        if(componentScan != null){
            String[] packages = componentScan.value();
            if(packages.length==0){// 如果没有给定扫描路径则默认扫描与当前类同一个包下的所有类
                packages = new String[]{cls.getPackage().getName()};
            }
            for(String pk: packages){
                Set<Class<?>> classSet = ClassUtil.getClasses(pk);/////////////需要一个给定包名返回类列表的函数
                for(Class<?> c: classSet){
                    Component component = c.getAnnotation(Component.class);
                    if(component != null){
                        loadComponent(c);
                    }
                }
            }
        }
    }

    /*
    * @Component
    * 自动注入组件
    * */
    private void loadComponent(Class<?> c){
        try {
            Object obj = c.newInstance();
            Field[] fields = c.getDeclaredFields();// 获得类的所有成员变量
            for(Field f: fields){
                Autowired aw = f.getAnnotation(Autowired.class);// 自动装配成员属性
                if(aw != null){
                    Object o = aw.name();
                    f.setAccessible(true);// 暴力反射，解除私有限定
                    if(map.get(f.getType()) == null)// 如果属性未注入，则注入设定的value值
                    {
                        f.set(obj, o);
                    }
                    else {// 如果属性已注入
                        f.set(obj, map.get(f.getType()));// 从已有的对象中找到 对象o的field对象标识的字段设置值
                    }
                }
            }
            map.put(c,obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*
     * @Configuration @Bean
     * 注入配置类中的bean
     * */
    private void loadBeans(Class<?> cls){
        Configuration con = cls.getAnnotation(Configuration.class);
        if(con != null){
            try {
                Object object = cls.newInstance();// 生成类的实体
                Method[] methods = cls.getDeclaredMethods();// 获取所有bean
                for(Method method: methods){
                    Bean bean = method.getAnnotation(Bean.class);
                    if(bean != null){
                        Object ret = method.invoke(object);
                        map.put(method.getReturnType(), ret);// 建立类与类实体的映射
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Object getBean(Class<?> cls){// 在外部进行强制类型转换
        return map.get(cls);
    }
}
