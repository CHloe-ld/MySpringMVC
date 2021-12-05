package springIoc.utils;

/*
 * ioc容器
 * */

import springIoc.annotation.*;
import springIoc.utils.ClassUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/*
 * mvc的ioc容器
 * 与原ioc容器的映射关系不同
 * <url地址，方法> <方法，类实体>
 * scope为单例
 * */

public class MVCApplicationContext {
    private Map<Class<?>, Object> map = new HashMap<>();// 类到实体的映射
    private Map<String, Method> methodMap = new HashMap<>();// url地址到方法的映射
    private Map<Method, Object> beanMap = new HashMap<>();// 方法到类实体的映射


    /*
     * 传入配置文件进行注入
     * */
    public MVCApplicationContext(Class<?>... clss){
        for(Class<?> cls: clss){
            loadBeans(cls);// 注入bean
            scanController(cls);// 扫描指定路径注入组件
        }
    }

    /*
    * @ComponentScan
    * 扫描指定路径注入
    * */
    private void scanController(Class<?> cls){
        ComponentScan componentScan = cls.getAnnotation(ComponentScan.class);
        if(componentScan != null){
            String[] packages = componentScan.value();// 需要扫描的路径
            if(packages.length == 0){
                packages = new String[]{"springIoc.controller"};// 默认扫描controller包
            }
            for(String pk: packages){
                Set<Class<?>> classSet = ClassUtil.getClasses(pk);// 获得指定目录下的所有类
                for(Class<?> c: classSet){
                    Controller controller = c.getAnnotation(Controller.class);
                    RequestMapping r = c.getAnnotation(RequestMapping.class);
                    String rootUrl = "";
                    if(r != null){
                        rootUrl = r.value();
                    }
                    if(controller != null){
                        try {
                            Object obj = c.newInstance();
                            Method[] methods = c.getDeclaredMethods();
                            for(Method method: methods){
                                RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                                String url = "";
                                if(requestMapping != null){
                                    // 组合url
                                    url = rootUrl + requestMapping.value();
                                }
                                methodMap.put(url, method);// 添加url到方法的映射
                                beanMap.put(method, obj);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
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


    /*
    * 获得bean
    * */
    @SuppressWarnings("unchecked")
    public <T> T getBean(Method m){
        return (T) beanMap.get(m);
    }

    public Method getMethod(String url){
        return methodMap.get(url);
    }

    public Map<String, Method> getMethodMap(){return methodMap;}

    public Map<Method, Object> getBeanMap(){return beanMap;}
}
