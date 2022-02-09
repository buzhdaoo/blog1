package com.blog.service;

import com.blog.dao.UserDao;
import com.blog.po.User;
import com.blog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author:Administrator
 * @DATE:2022/1/25/025 Description:MD5加密
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public User checkUser(String username, String password) {
        User user=userDao.findByUsernameAndPassword (username, MD5Utils.code (password));
        return user;
    }
}
