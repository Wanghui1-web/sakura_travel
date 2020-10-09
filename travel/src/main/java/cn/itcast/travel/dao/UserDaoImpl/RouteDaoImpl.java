package cn.itcast.travel.dao.UserDaoImpl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.hamcrest.Condition;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
@SuppressWarnings("unchecked")
public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public int findTotalCount(int cid, String rname) {
//        String sql="select count(*) from tab_route where cid= ?"
        String sql=" select count(*) from tab_route where 1 = 1 ";
        StringBuilder stringBuilder = new StringBuilder(sql);
        List params=new ArrayList();
        if (cid!=0){
            stringBuilder.append(" and cid = ? ");
            params.add(cid);
        }
        if (rname!=null&&rname.length()>0){
            stringBuilder.append(" and rname like ? ");
            params.add("%"+rname+"%");
        }
        sql=stringBuilder.toString();
        Integer integer = template.queryForObject(sql, Integer.class, params.toArray());
        return integer;
    }

    @Override
    public List<Route> findByPage(int cid, int start, int pagesize, String rname) {
//        String sql="select *from tab_route where cid=? limit ?,?";
        String sql=" select *from tab_route where 1 = 1 ";
        StringBuilder stringBuilder1 = new StringBuilder(sql);
        List params=new ArrayList();
        if (cid!=0){
            stringBuilder1.append(" and cid = ? ");
            params.add(cid);
        }
        if (rname!=null&&rname.length()>0){
            stringBuilder1.append(" and rname like ? ");
            params.add("%"+rname+"%");
        }
        stringBuilder1.append("limit ? , ? ");
        params.add(start);
        params.add(pagesize);
        sql=stringBuilder1.toString();
        List<Route> list = template.query(sql, new BeanPropertyRowMapper<Route>(Route.class),params.toArray());
        return list;
    }

    @Override
    public Route findOne(int rid) {
        String sql="select * from tab_route where rid = ?";
        return template.queryForObject(sql,new BeanPropertyRowMapper<Route>(Route.class),rid);
    }
}
