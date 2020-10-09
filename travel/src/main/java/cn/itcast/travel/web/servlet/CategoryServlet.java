package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.service.impl.CategoryServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {
    private CategoryService categoryService=new CategoryServiceImpl();
    /*
    查询所有的分类信息
     */
    public void findAll(HttpServletRequest request,HttpServletResponse response) throws ServletException{
        List<Category> categoryList = categoryService.findAll();
//        ObjectMapper objectMapper=new ObjectMapper();
//        response.setContentType("application/json;charset");
        writeValue(categoryList,response);
    }
    /*
   分页展示
     */

}
