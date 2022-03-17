package com.yf.blog.service.impl;

import com.yf.blog.entity.User;
import com.yf.blog.mapper.UserMapper;
import com.yf.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User checkUser(String username, String password) {   // d6ef5f7fa914c19931a55bb262ec879c
        User user = userMapper.findByUsernameAndPassword(username, DigestUtils.md5DigestAsHex(password.getBytes()));
        return user;
    }
}
