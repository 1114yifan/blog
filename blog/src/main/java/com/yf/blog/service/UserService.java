package com.yf.blog.service;

import com.yf.blog.entity.User;

/**
 * @Description:
 */
public interface UserService {
    /**
     * 根据账户和密码查询管理员账户
     * @param username
     * @param password
     * @return
     */
    User checkUser(String username, String password);
}
