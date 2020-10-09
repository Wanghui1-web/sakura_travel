package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.UserDaoImpl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {
    private  UserDao userDao=new UserDaoImpl();
    //注册用户
    @Override
    public boolean register(User user) {
        User u = userDao.findByUsername(user.getUsername());
        if (u!= null){
            return false;
        }
        user.setCode(UuidUtil.getUuid());
        user.setStatus("N");
        userDao.savaUser(user);
        String conteny="<a href='http://localhost/travel/user/active?code="+user.getCode()+"'>点击激活【黑马旅游网】</a>";
        MailUtils.sendMail(user.getEmail(),conteny,"激活邮件");
        return true;
    }

    @Override
    public Boolean active(String code) {
        //根据激活码查询用户对象
        User user = userDao.findByCode(code);
        userDao.updateStatus(user);
        //调用dao修改激活状态
        return null;
    }
/*
登录方法
 */
    @Override
    public User login(User user) {

        return userDao.findByUsernameAndPassword(user.getUsername(),user.getPassword());
    }
}
