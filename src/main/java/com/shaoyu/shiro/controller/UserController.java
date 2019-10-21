package com.shaoyu.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller

public class UserController {

    @RequestMapping("/add")
    public String add() {
        return "/user/add";
    }

    @RequestMapping("/update")
    public String update() {
        return "/user/update";
    }

    @RequestMapping("/login")
    public String toLogin() {
        return "/login";
    }


    @RequestMapping("/logout")
    public String logout() {
        return "/logout";
    }

    @RequestMapping("/noAuth")
    public String noAuth() {
        return "/noAuth";
    }

    /**
     * @return
     */
    @RequestMapping("/test")
    public String test() {
        return "test";
    }


    @RequestMapping("/toLogin")
    public String login(String username, String password, Model model) {

        // 获取subject, 主体与证书
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()){
            // 封装用户数据,获取token
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            // 提交主体与证书
            token.setRememberMe(true);
            try {
                // 跳转 doGetAuthenticationInfo
                currentUser.login(token);
                System.out.println("toker:" + token);
                return "test";
            } catch (UnknownAccountException e) {
                //  登录失败:用户名不存在
                model.addAttribute("msg", "用户名不存在");
                return "/login";
            } catch (IncorrectCredentialsException e) {
                //登录失败:密码错误
                model.addAttribute("msg", "密码错误");
                return "/login";
            }
        }
        else {
            return "test";
        }

    }


}
