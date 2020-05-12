package com.itdan.shiro.demo.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

/**
 * 自定义Realm
 */

public class UserRealm extends AuthorizingRealm {

    /**
     * 执行权限逻辑
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        //进行授权
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();


         //获取当前登入用户信息
        Subject subject = SecurityUtils.getSubject();
          //通过用户信息去数据库查询出当前用户所具有的角色和权限

        //添加资源的授权字符串
        info.addStringPermission("user:add");
        return info;
    }

    /**
     * 执行认证逻辑
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //调用 subject.login(token);会先进入到认证逻辑

        //先设置假数据
        String username="dan";
        String password="123";

        //先获取到登入用户信息
        UsernamePasswordToken token=(UsernamePasswordToken)authenticationToken.getPrincipal();

        //根据用户名判断用户是否存在
        if(!token.getUsername().equals(username)){
             return null;
             //表示用户不存在，返回null就会抛出UnknownAccountException
        }
       //判断密码
        return new SimpleAuthenticationInfo("",password,"");
    }
}
