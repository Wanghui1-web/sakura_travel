package cn.itcast.travel.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //在这里完成方法的分发优化servlet
        //完成方法的分发
        //获取请求路径
        String realuri = req.getRequestURI();
//        System.out.println("虚拟路径请求的url：" + realuri);
        //获取方法名称
        String methodName = realuri.substring(realuri.lastIndexOf("/") + 1);
//        System.out.println("方法名称" + methodName);
        //获取方法对象
        //this谁调用我我代表谁
//        System.out.println(this);
        //方法的分发
        try {
            //这个方法是获得忽略访问权限getDel
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
//            暴力反射
//            method.setAccessible(true);
            method.invoke(this,req,resp);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //执行方法
        //
//        super.service(req, resp);
    }
    /*
    将传入的的对象序列化为json并且写会客户端
     */
    public void writeValue(Object obj,HttpServletResponse response){
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        try {

            objectMapper.writeValue(response.getOutputStream(),obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*
    将传入的对象序列化为json返回给使用者
     */
    public String writeValueAsString(Object obj) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}
