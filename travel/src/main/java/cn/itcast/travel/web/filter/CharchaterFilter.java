package cn.itcast.travel.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class CharchaterFilter implements Filter {
    public void destroy() {

    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
//        System.out.println("到这里了");
        //将父接口转为子接口
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response=(HttpServletResponse)resp;
        //解决post请求中文乱码问题
        String method = request.getMethod();
        if (method.equalsIgnoreCase("post")){
            request.setCharacterEncoding("utf-8");
        }
        //处理响应编码
        response.setContentType("text/html;charset=utf-8");

        chain.doFilter(request, response);
//        System.out.println("回去了");
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
