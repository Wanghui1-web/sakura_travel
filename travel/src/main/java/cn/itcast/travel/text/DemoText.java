package cn.itcast.travel.text;

import cn.itcast.travel.dao.CatagoryDao;
import cn.itcast.travel.dao.UserDaoImpl.CatagoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.CategoryServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;
import org.junit.Test;

import java.util.List;

public class DemoText {
@Test
    public void demotext(){
//    CategoryService categoryService=new CategoryServiceImpl();
//    System.out.println(categoryService.findAll());
    RouteService routeService=new RouteServiceImpl();
    PageBean<Route> routePageBean1 = routeService.pageQuery(5, 7, 5, null);
    System.out.println(routePageBean1);


}
}
