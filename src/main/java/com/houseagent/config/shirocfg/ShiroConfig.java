package com.houseagent.config.shirocfg;

import com.houseagent.realm.UserRealm;
import com.houseagent.realm.AgentRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
//    @Autowired
//    private UserRealm userRealm;

    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }

    @Bean
    public DefaultWebSecurityManager getDefaultSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("getDefaultSecurityManager") DefaultWebSecurityManager manager) {
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        filterFactoryBean.setSecurityManager(manager);
        //添加需要过滤的url
        Map<String, String> map = new LinkedHashMap<>();
        map.put("/house/findAll", "anon");//都可以访问
//        map.put("/house/manage", "authc");//用户认证登录才可以访问
//        map.put("/user/update", "authc");//用户认证登录才可以访问
//        map.put("/agent/update", "authc");//用户认证登录才可以访问
        filterFactoryBean.setFilterChainDefinitionMap(map);
        //前端返回对应的login.jsp
        filterFactoryBean.setLoginUrl("/login");//未认证，被拦截后跳到这个页面
//        filterFactoryBean.setUnauthorizedUrl("/unauth");//未授权拦截后跳到这个页面

        return filterFactoryBean;
    }


}
