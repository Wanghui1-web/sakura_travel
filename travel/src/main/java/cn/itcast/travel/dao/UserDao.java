package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {
    //根据用户名查询用户对象
    //保存用户信息
    public User findByUsername(String username);

    public void savaUser(User user);

    User findByCode(String code);

    void updateStatus(User user);



    User findByUsernameAndPassword(String username, String password);
}
