package com.shaoyu.shiro.dao;

import lombok.Data;

@Data
public class User {
    Integer id;

    String username;

    String password;

    String auth;
}
