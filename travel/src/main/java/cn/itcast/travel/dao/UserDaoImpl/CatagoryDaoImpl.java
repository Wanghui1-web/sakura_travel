package cn.itcast.travel.dao.UserDaoImpl;

import cn.itcast.travel.dao.CatagoryDao;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;


import java.util.List;

public class CatagoryDaoImpl implements CatagoryDao {
    private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public List<Category> findAll() {
        String sql="select *from tab_category";
        List<Category> list = template.query(sql, new BeanPropertyRowMapper<Category>(Category.class));
        return list;
    }
}
