package com.gxweilan.DiNiuWang.config;

import com.gxweilan.DiNiuWang.entity.WxUser;
import com.gxweilan.DiNiuWang.serviceAndImpl.WxUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author 墨杨清竹
 * @version 1.0
 * @date 2023/2/6 15:10
 */
public class ShiroRealm extends AuthorizingRealm {
    @Autowired
    WxUserService wxUserService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了=>授权doGetAuthorizationInfo");
        return new SimpleAuthorizationInfo();
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行了=>认证doGetAuthenticationInfo");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        WxUser wxUser = wxUserService.selWxUserInfoByMd5Openid(token.getUsername());
        if (wxUser == null) {
            return null;
        }
        return new SimpleAuthenticationInfo("", "123456", "");
    }
}
