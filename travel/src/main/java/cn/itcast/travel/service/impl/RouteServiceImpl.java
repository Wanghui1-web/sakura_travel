package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.UserDaoImpl.FavoriteDaoImpl;
import cn.itcast.travel.dao.UserDaoImpl.RouteDaoImpl;
import cn.itcast.travel.dao.UserDaoImpl.RouteImgDaoImpl;
import cn.itcast.travel.dao.UserDaoImpl.SellerDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    private RouteDao routeDao=new RouteDaoImpl();
//    private RouteImg routeImg= new RouteImg();
    private RouteImgDao routeImgDao=new RouteImgDaoImpl();
    private SellerDao sellerDao=new SellerDaoImpl();
    private FavoriteDao favoriteDao=new FavoriteDaoImpl();
    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname) {

    PageBean<Route> pb=new PageBean<Route>();
        pb.setPageSize(pageSize);//设置每页显示个数
                pb.setCurrentPage(currentPage);//设置当前页码
                int totalCount=routeDao.findTotalCount(cid,rname);
                pb.setTotalCount(totalCount);//设置总记录数
                //设置当前页显示的集合
                int start=((currentPage-1)*pageSize);
                List<Route>  list=routeDao.findByPage(cid, start, pageSize,rname);
        pb.setList(list);
        //设置总页数
        int totalPage=totalCount%pageSize==0?totalCount/pageSize:(totalCount/pageSize)+1;
        pb.setTotalPage(totalPage);

        return pb;
        }

    @Override
    public Route findOne(String rid) {
        //根据id去route表中查询route对象
        Route one = routeDao.findOne(Integer.parseInt(rid));
        //根据route的id查询图片的集合信息
        List<RouteImg> routeImgs = routeImgDao.findByRid(one.getRid());
        //设置到route中
        one.setRouteImgList(routeImgs);
        //根据route的sid查询卖家信息
        Seller byId = sellerDao.findById(one.getSid());
        one.setSeller(byId);
        //查询收藏次数
        int count = favoriteDao.findCountByRid(one.getRid());
        one.setCount(count);
        return one;
    }
}
