package com.itdan.shiro.demo.shiro;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro配置类
 */
@Configuration
public class ShiroConfig {

    //1.创建 ShiroFilterFactoryBean
    @Bean
    public  ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager){
           ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
           shiroFilterFactoryBean.setSecurityManager(securityManager);

           //添加shiro的内置拦截器
        /**
         * 常用的过滤器：
         * anon:无需认证(登入)就可以访问资源
         * authc:必须认证才可以访问资源
         * user:使用rememberMe功能即可访问资源
         * perms:必须具备资源权限才可以访问
         * role:必须具有角色权限才可以访问
         * */
         Map<String,String> filterMap=new LinkedHashMap<>();//为了保证访问循序可以使用LinkedHashMap

         //添加拦截规则
         //filterMap.put("/toAdd","authc");
         //filterMap.put("/toUpdate","authc");
         //如果拦截的界面在同一个文件夹下，可以使用统一拦击
         //filterMap.put("/user/*","authc");

          filterMap.put("/login","anon");//放行登入
          filterMap.put("/toAdd","perms:[user:add]");//授权过滤器
          filterMap.put("/*","authc");
         //修改登入界面
        shiroFilterFactoryBean.setLoginUrl("/toLogin");
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauth");//设置未授权界面

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);

        return shiroFilterFactoryBean;
    }

    //2.DefaultWebSecurityManager
    @Bean
    public  DefaultWebSecurityManager defaultWebSecurityManager(final UserRealm userRealm){
         DefaultWebSecurityManager securityManager =new DefaultWebSecurityManager();
         securityManager.setRealm(userRealm);
         return securityManager;
    }

    //3.创建Realm
    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }

}
