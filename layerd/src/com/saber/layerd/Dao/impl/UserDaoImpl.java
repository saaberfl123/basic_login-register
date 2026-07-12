package com.saber.layerd.Dao.impl;

import com.saber.layerd.Dao.UserDao;
import com.saber.layerd.Util.JDBCUtil;
import com.saber.layerd.module.User;

import java.util.List;

public class UserDaoImpl implements UserDao
{
    @Override
    public String saveUser(String username, String password, String salt) {
        String sql="INSERT INTO `user` (`username`, `password`, `salt`) VALUES (?,?,?);";
        Object[] params={username,password,salt};
        JDBCUtil.UpDate(sql,params);
        return "保存用户成功";
    }

    @Override
    public User getUserByUsername(String username) {
        String sql="SELECT * FROM `user` WHERE `username` = ?;";
        Object[] params={username};
        List<User> list=JDBCUtil.Query(sql,User.class,params);
        return list.isEmpty() ? null:list.get(0);
    }

}
