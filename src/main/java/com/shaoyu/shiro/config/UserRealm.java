package com.shaoyu.shiro.config;

import com.shaoyu.shiro.dao.User;
import com.shaoyu.shiro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 授权 三要素：权限、角色、用户
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("授权");

//        第一种授权方式
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPreviousPrincipals();

        User dbUser = userService.findById(user.getId());

//      有问题
        info.addStringPermission(dbUser.getAuth());
        return info;
    }

    /**
     * 认证
     *
     * @param arg0
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
        System.out.println("开始认证");
//        AuthenticationToken强转为UsernamePasswordToken
        UsernamePasswordToken token = (UsernamePasswordToken) arg0;
        System.out.println(token);
        String username = token.getUsername();
        User user = userService.findByUsername(username);
        if (user == null) {
            return null;
        }

//          密码
        Object principal = username;
        Object credentials = user.getPassword();
        String realmName = getName();
//
//        ByteSource credentialsSalt = ByteSource.Util.bytes(username);


        return new SimpleAuthenticationInfo(principal,     //认证的主体信息
                credentials,     //密码
//                credentialsSalt, //加盐
                realmName);    //realm对象的name
    }
}
