package com.yf.blog.mapper;

import com.yf.blog.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Description:  用户持久层接口
 */
@Repository // 自动装配
public interface UserMapper {

    /**
     * 根据账户和密码查询管理员账户
     * @param username
     * @param password
     * @return
     */
    User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}
