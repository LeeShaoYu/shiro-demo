package com.shaoyu.shiro.service.serviceImpl;

import com.shaoyu.shiro.dao.User;
import com.shaoyu.shiro.mapper.UserMapper;
import com.shaoyu.shiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public User findById(Integer id) {
        return  userMapper.findById(id);
    }

}
