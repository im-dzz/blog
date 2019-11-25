package com.imdzz.blog.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author imdzz
 * @version 1.0
 * @date 2019/10/16 10:36
 */
@Configuration
public class ShiroConfig {
    /**
     * 配置角色、权限和路径的关系
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        Map<String, String> filterChainDefinitionMap = new HashMap<String, String>();
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthc");  //权限不足跳转的url
        shiroFilterFactoryBean.setSuccessUrl("/home/index");  // 登录成功后跳转到此url

        filterChainDefinitionMap.put("/*", "anon");  // 所有用户可访问
        filterChainDefinitionMap.put("/blogs/findById/*", "authc");   // 已登录用户可访问
//        filterChainDefinitionMap.put("/authc/admin", "roles[admin]");   // 指定角色可访问
//        filterChainDefinitionMap.put("/authc/renewable", "perms[Create,Update]");  // 指定权限的用户可访问
//        filterChainDefinitionMap.put("/authc/removable", "perms[Delete]");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 配置密码散列
     * @return
     */
//    @Bean
//    public HashedCredentialsMatcher hashedCredentialsMatcher() {
//        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
//        hashedCredentialsMatcher.setHashAlgorithmName(PasswordHelper.ALGORITHM_NAME); // 散列算法
//        hashedCredentialsMatcher.setHashIterations(PasswordHelper.HASH_ITERATIONS); // 散列次数
//        return hashedCredentialsMatcher;
//    }

    /**
     * 使用上面方法返回的对象创建一个权限验证类
     * @return
     */
    @Bean
    public CustomerShiroRealm shiroRealm() {
        CustomerShiroRealm shiroRealm = new CustomerShiroRealm();
        //shiroRealm.setCredentialsMatcher(hashedCredentialsMatcher()); // 原来在这里
        return shiroRealm;
    }

    /**
     * 使用上面的方法创建一个安全管理类
     * @return
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm());
        return securityManager;
    }

//    @Bean
//    public PasswordHelper passwordHelper() {
//        return new PasswordHelper();
//    }
}
