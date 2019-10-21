package com.shaoyu.shiro.service;

import com.shaoyu.shiro.dao.User;

public interface UserService {
    User findByUsername(String username);

    User findById(Integer id);
}
