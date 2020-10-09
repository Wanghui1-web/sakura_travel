package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CatagoryDao;
import cn.itcast.travel.dao.UserDaoImpl.CatagoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    private CatagoryDao catagoryDao = new CatagoryDaoImpl();

    @Override
    public List<Category> findAll() {
        //进行缓存的优化
        //使用redis进行数据优化
        //        // 从redis中查询
        // 判断集合是否为空
        //如果为空,就从数据库中查询，将数据库存入jedis
        //如果不为空直接返回
        List<Category> list = null;
        Jedis jedis = new Jedis();
//        Set<String> category = jedis.zrange("category", 0, -1);//这个不能查到id
        //下面这个可以查到cid（分数）
        Set<Tuple> category = jedis.zrangeWithScores("category", 0, -1);
        if (category == null || category.size() == 0) {
//            System.out.println("从数据库查询");
            list = catagoryDao.findAll();
            //将集合数据存入redis中category的key
            for (int i = 0; i < list.size(); i++) {
                jedis.zadd("category", list.get(i).getCid(), list.get(i).getCname());
            }
        }else {
//            System.out.println("从缓存中查询查询");
            //如果返回值不为空转换为list
            list=new ArrayList<Category>();
            for(Tuple tuple:category){
                Category category1 = new Category();
                category1.setCname(tuple.getElement());
                category1.setCid((int)tuple.getScore());
                list.add(category1);
            }
        }
//        System.out.println(list);
        return list;
    }
}



