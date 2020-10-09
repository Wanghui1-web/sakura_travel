package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private RouteService routeService=new RouteServiceImpl();
    private FavoriteService favoriteService=new FavoriteServiceImpl();
    /*
    分页查询
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr= request.getParameter("pageSize");
        String cidStr = request.getParameter("cid");
        String rname = request.getParameter("rname");
        rname=new String(rname.getBytes("iso-8859-1"),"utf-8");
//        int currentPage=0;
//        currentPage=changType(currentPageStr);
//        if (currentPage==0){
//            currentPage=1;
//        }
//        int pageSize=0;
//        pageSize=changType(pageSizeStr);
//        if (pageSize==0){
//            pageSize=5;
//        }
//        int cid=0;
//        cid=changType(cidStr);
        int currentPage=0;//当前页码
        if (currentPageStr!=null&&currentPageStr.length()>0){
            currentPage=Integer.parseInt(currentPageStr);
        }else {
            currentPage=1;
        }
        int pagesize=0;//每页显示数据
        if (pageSizeStr!=null&&pageSizeStr.length()>0){
            pagesize=Integer.parseInt(pageSizeStr);
        }else {
            pagesize=5;
        }
        int cid=0;//类别id
        if (cidStr!=null&&cidStr.length()>0&&!"null".equals(cidStr)){
            cid=Integer.parseInt(cidStr);
        }
        //调用service
        PageBean<Route> routePageBean = routeService.pageQuery(cid, currentPage, pagesize,rname);
        writeValue(routePageBean,response);


    }
//    public static void main(String []args){
//        RouteServlet routeServlet = new RouteServlet();
//        String i="";
//        int i1 = routeServlet.changType(i);
//        System.out.println(i1);
//
//    }
//    static int changType(String str){
//        int i=0;
//        if (str!=null&&str.length()>0){
//            i=Integer.parseInt(str);
//        }else {
//            i=0;
//        }
//        return i;
//    }
    /*
    详情页展示
     */
     public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         String rid = request.getParameter("rid");
         Route route =  routeService.findOne(rid);
         writeValue(route,response);
     }
     //判断当前用户是否收藏
     public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String rid = request.getParameter("rid");
        User user = (User) request.getSession().getAttribute("user");
        int uid;
        if (user==null){
            uid=0;
        }else {
            uid=user.getUid();
        }
        boolean flag = favoriteService.isFavorite(rid, uid);
        writeValue(flag,response);
    }
    //收藏线路
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String rid = request.getParameter("rid");
        User user = (User) request.getSession().getAttribute("user");
        int uid;
        if (user==null){
            return ;
        }else {
            uid=user.getUid();
        }
        favoriteService.add(rid,uid);
    }

}
