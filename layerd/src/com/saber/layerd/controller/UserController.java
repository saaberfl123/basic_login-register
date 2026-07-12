package com.saber.layerd.controller;

import com.saber.layerd.Dao.impl.UserDaoImpl;
import com.saber.layerd.service.UserService;
import com.saber.layerd.service.impl.UserServiceImpl;

import java.util.List;

/**
 * 控制层
 */
public class UserController
{
    //控制层调用service层
    UserServiceImpl service = new UserServiceImpl();
    public String register(String username, String password)
    {
        return service.register(username,password);
    }

    public String login(String username, String password)
    {
        return service.login(username,password);
    }
}
