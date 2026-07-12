package com.saber.layerd.Dao;

import com.saber.layerd.module.User;

/**
 * 数据访问层接口
 */
public interface UserDao
{
    public String saveUser(String username, String password,String salt);
    public User getUserByUsername(String username);
}
