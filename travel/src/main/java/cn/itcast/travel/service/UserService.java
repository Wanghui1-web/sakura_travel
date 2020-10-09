package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

public interface UserService {

    boolean register(User user); //注册用户

    Boolean active(String code);//

    User login(User user);
}
