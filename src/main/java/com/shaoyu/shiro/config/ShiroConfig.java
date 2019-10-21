package com.shaoyu.shiro.config;

import org.apache.shiro.authc.pam.*;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;

@Configuration
public class ShiroConfig {

    /*
    *   Shiro的架构主要有三个顶级概念：Subject, SecurityManager和Realms
    * */


    /**
     *  Realms在Shiro和你的安全数据之间扮演“桥梁”或“连接器”的角色。
     *  当需要用到安全数据比如用户帐号来进行认证或授权时，
     *  Shiro会从应用配置的一个或多个Realms中来查找。
     * 1、Realm
     * @return
     */
    @Bean("userRealm")
    public UserRealm getRealm() {
        return new UserRealm();
    }

    /**
     * 2、SecurityManager是Shiro架构的核心，起协调内部各组件的作用。同样也管理Shiro的所有用户，可以进行用户相关的安全操作。
     * @param userRealm
     * @return
     */
    @Bean("securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
/*//        多realm配置
        Collection<Realm> realmCollection = new HashSet<Realm>();
        realmCollection.add(userRealm);
        realmCollection.add(userRealm2);
        ModularRealmAuthenticator modularRealmAuthenticator = new ModularRealmAuthenticator();
        modularRealmAuthenticator.setRealms(realmCollection);
        *//**
         认证策略
         * @see AllSuccessfulStrategy
         * @see AtLeastOneSuccessfulStrategy
         * @see FirstSuccessfulStrategy
         * @since 0.2
         *//*

        AuthenticationStrategy authenticationStrategy = new AllSuccessfulStrategy();
        modularRealmAuthenticator.setAuthenticationStrategy(authenticationStrategy);*/
        return securityManager;
    }

    /**
     *
     * @param defaultWebSecurityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
//        安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
/*        内置过滤器
        anon： 无需认证，登录即可
        authc: 认证才可以访问
        user:   rememberMe()即可访问
        logout:    注销用户
        perms: 资源必须得到权限才可以访问
        roles:  资源必须得到角色权限才可以访问
        */

        LinkedHashMap<String, String> filterMap = new LinkedHashMap<String, String>();
//        过滤器配置
        filterMap.put("/test", "anon");
        filterMap.put("/toLogin", "anon");

        filterMap.put("/add", "perms[user:add]");
        filterMap.put("/add", "perms[user:update]");
        filterMap.put("/logout", "logout");
        filterMap.put("/*", "authc");


        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
//        设置过滤后跳转的登录页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setUnauthorizedUrl("/noAuth");
        return shiroFilterFactoryBean;
    }
}
