package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*") //user/add /user/find??
public class UserServlet extends  BaseServlet{
    private UserService userService = new UserServiceImpl();

    /*
    激活功能
     */
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        if (code != null) {
//            UserService userService= new UserServiceImpl();
            Boolean flag = userService.active(code);
            String msg = null;
            if (flag) {
                msg = "激活成功，请<a href='login.html'>登录</a>";
            } else {
                msg = "激活失败,请联系管理员";
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }
    }

    /*
    退出功能
     */
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        System.out.println("userservlet的find方法、、、、、、");
        //session自己干掉自己
        request.getSession().invalidate();
        //重定向到登录界面
        response.sendRedirect(request.getContextPath() + "/login.html");
    }

    /*
    登录显示查询单个
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");
//        System.out.println(user);
        writeValue(user,response);
//        ObjectMapper objectMapper = new ObjectMapper();
//        response.setContentType("application/json;charset=utf-8");
//        objectMapper.writeValue(response.getOutputStream(), user);
    }

    /*
    登录功能
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
//        UserService userService=new UserServiceImpl();
        User user1 = userService.login(user);
        request.getSession().setAttribute("user", user1);
        ResultInfo info = new ResultInfo();
        if (user1 == null) {
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误！");
        }
        if (user1 != null && "N".equals(user1.getStatus())) {
            info.setFlag(false);
            info.setErrorMsg("你尚未激活，请登录邮箱激活");
        }
        if (user1 != null && "Y".equals(user1.getStatus())) {
            info.setFlag(true);
        }
        //将info信息写回到前端
//        writeValue(info,response);
       ObjectMapper objectMapper = new ObjectMapper();
       response.setContentType("application/json;charset=utf-8");
       objectMapper.writeValue(response.getOutputStream(), info);
    }

    /*
    注册功能
     */
    public void reginster(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //验证码校验
        HttpSession session = request.getSession();
        String session_check = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        String check = request.getParameter("check");
        //验证码错误
        if (session_check == null || !session_check.equalsIgnoreCase(check)) {
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("注册失败，验证码错误");
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            //响应结果
            //设置content-type
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
            return;
        }

        //获取数据
        Map<String, String[]> parameterMap = request.getParameterMap();
        //封装对象
        User user = new User();
        try {
            BeanUtils.populate(user, parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //调用方法
//        UserService userService = new UserServiceImpl();
        boolean flag = userService.register(user);
        ResultInfo resultInfo = new ResultInfo();
        //响应数据
        if (flag) {
            //注册成功
            resultInfo.setFlag(true);
        } else {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("注册失败");
        }
        //将info序列化为json，写会客户端
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(resultInfo);
        //响应结果
        //设置content-type
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
        Cookie[] cookies = request.getCookies();

    }
//    public void (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
//
//    }


}
