package com.saber.layerd.service.impl;

import com.saber.layerd.Dao.impl.UserDaoImpl;
import com.saber.layerd.Util.saltUtil;
import com.saber.layerd.module.User;
import com.saber.layerd.service.UserService;

/**
 * 业务层
 */
public class UserServiceImpl implements UserService
{
    //service层调用Dao层
    UserDaoImpl UserDaoImpl = new UserDaoImpl();

    @Override
    public String register(String username, String password)
    {
        String salt = saltUtil.generateSalt(30);
        String realPassword = saltUtil.md5WithSalt(password, salt);
        return UserDaoImpl.saveUser(username, realPassword, salt);
    }

    @Override
    public String login(String username, String password)
    {
        User user = UserDaoImpl.getUserByUsername(username);
        if(user==null)return "用户不存在";
        String realPassword = user.getPassword();
        String InPassword=saltUtil.md5WithSalt(password, user.getSalt());
        if(realPassword.equals(InPassword))return "登录成功";
        return "登录失败";
    }
}
