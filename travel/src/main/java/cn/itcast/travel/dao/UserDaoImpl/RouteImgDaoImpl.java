package cn.itcast.travel.dao.UserDaoImpl;

import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RouteImgDaoImpl implements RouteImgDao {
//    private RouteImg routeImg = new RouteImg();
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    /*
    根据rid查询图片信息
     */
    @Override
    public List<RouteImg> findByRid(int rid) {
        String sql = "select *from tab_route_img where rid = ?";
        List<RouteImg> query = jdbcTemplate.query(sql, new BeanPropertyRowMapper<RouteImg>(RouteImg.class), rid);
        return query;
    }
}
