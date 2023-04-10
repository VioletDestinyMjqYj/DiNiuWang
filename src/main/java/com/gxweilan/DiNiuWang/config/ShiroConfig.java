package com.gxweilan.DiNiuWang.config;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 墨杨清竹
 * @version 1.0
 * @date 2023/2/6 14:18
 */
@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);
        //anon:无需认证可访问, authc:必须认证才可访问

        //拦截
        Map<String, String> filterMap = new LinkedHashMap<>();


        //测试接口
        filterMap.put("/**", "anon");
        filterMap.put("/api/test", "anon");
        filterMap.put("/login", "anon");
        bean.setFilterChainDefinitionMap(filterMap);

        //设置登陆请求
        bean.setLoginUrl("/login");
        return bean;
    }


    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("shiroRealm") ShiroRealm shiroRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm);
        return securityManager;
    }

    @Bean(name = "shiroRealm")
    public ShiroRealm shiroRealm() {
        return new ShiroRealm();
    }
}
