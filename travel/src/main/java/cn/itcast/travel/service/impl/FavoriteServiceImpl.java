package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.UserDaoImpl.FavoriteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.service.FavoriteService;

public class FavoriteServiceImpl implements FavoriteService {
    private FavoriteDao favoriteDao=new FavoriteDaoImpl();
    @Override
    public boolean isFavorite(String rid, int uid) {
        boolean flag;
        Favorite byRidAndUid = favoriteDao.findByRidAndUid(Integer.parseInt(rid), uid);
        //如果有值就返回true，没有就返回false
        if (byRidAndUid == null){
            flag=false;
        }else {
            flag=true;
        }
        return flag;
    }

    @Override
    public void add(String rid, int uid) {
        favoriteDao.add(Integer.parseInt(rid),uid);
    }
}
