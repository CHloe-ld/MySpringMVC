package springIoc.servlet;

import springIoc.Configure;
import springIoc.controller.UserController;
import springIoc.utils.MVCApplicationContext;
import springIoc.utils.uriMatcher;
import springIoc.view.View;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public class DispatcherServlet extends HttpServlet {
    MVCApplicationContext mvcApplicationContext;

    public DispatcherServlet(){
        super();
    }

    @Override
    public void init(){
        System.out.println("init");
        // 导入配置文件注入
        mvcApplicationContext = new MVCApplicationContext(Configure.class);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String usrUri = request.getRequestURI();
        request.setCharacterEncoding("utf-8");// 设置字符编码方式
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/html;charset=utf-8");
        System.out.println("用户访问的uri为：" + usrUri);
//        System.out.println("request.getContextPath:" + request.getContextPath());
//        System.out.println("request.getRequestURI:" + request.getRequestURI());
//        System.out.println("request.getRequestURL:" + request.getRequestURL());
//        System.out.println("request.getServletPath:" + request.getServletPath());

        /*
        * 将用户访问的uri与已有的uri进行匹配，同时对用户传递的参数进行读取
        * */
        ArrayList<String> attribute = new ArrayList<>();  // 用户访问传递的参数
        Method method = null;
        boolean matched = false;
        // 遍历methodMap
        for (String mapUri : mvcApplicationContext.getMethodMap().keySet()) {
            if (mapUri == null)
                break;
            Method mapMethod = mvcApplicationContext.getMethodMap().get(mapUri);
            String[] splitUsrUri = usrUri.split("/");
            String[] splitMapUri = mapUri.split("/");
            matched = true;
            // 长度不同直接跳过
            if (splitMapUri.length != splitUsrUri.length) {
                matched = false;
                continue;
            }
            for (int i = 0; i < splitUsrUri.length; i++) {
//                System.out.println("splitMapUri[i]:" + splitMapUri[i] + "  splitUsrUri[i]：" + splitUsrUri[i]);
                if (splitMapUri[i].startsWith("{") && splitMapUri[i].endsWith("}")) {// 传参位
                    attribute.add(splitUsrUri[i]);

                } else {
                    if (!Objects.equals(splitUsrUri[i], splitMapUri[i])) {
                        matched = false;
                        break;
                    }
                }
            }
            if (matched) {
                method = mapMethod;
                break;
            }
            attribute = new ArrayList<>();
        }
        if(matched){
            System.out.println("匹配成功，应调用的方法为" + method.getName());
            System.out.println("用户传递的参数为" + attribute);
        }
        else{
            System.out.println("匹配失败");
        }

        /*
        * 处理用户请求，渲染界面
        * */
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        Object obj = mvcApplicationContext.getBean(method);
        String[] attr = attribute.toArray(new String[0]);
        try {
            assert method != null;
            View res = (View) method.invoke(obj, attr);
            String path = res.getPath();
            // 如果返回值不为空
            if(path.endsWith(".jsp")){// 路径为jsp文件，则请求转发（渲染jsp文件）
                System.out.println("请求转发");
                request.getRequestDispatcher(path).forward(request, response);
            }
            else{// 路径不以jsp结尾，则重定向
                System.out.println("重定向路径：" + res.getPath());
//                response.setHeader("Location", res.getPath());
                response.sendRedirect(res.getPath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        out.close();

    }
    public void destroy(){}
}

