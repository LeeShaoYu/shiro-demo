package com.shaoyu.shiro.mapper;

import com.shaoyu.shiro.dao.User;

public interface UserMapper {

    User findByUsername(String username);

    User findById(Integer id);
}
