package com.imdzz.blog.config;

import com.imdzz.blog.model.Permission;
import com.imdzz.blog.model.Role;
import com.imdzz.blog.model.User;
import com.imdzz.blog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用来执行认证、授权的类，它是应用程序与安全数据之间的桥梁
 * @author imdzz
 * @version 1.0
 * @date 2019/10/16 10:12
 */
@Slf4j
public class CustomerShiroRealm extends AuthorizingRealm {
    @Autowired
    UserService userService;

    /**
     * 读取用户的角色和权限信息
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        String username = (String) principalCollection.getPrimaryPrincipal();
        User user = userService.findUserByName(username);
        authorizationInfo.addRoles(userService.getUserRoles(user.getId()));
        authorizationInfo.addStringPermissions(userService.getUserPermissions(user.getId()));

        return authorizationInfo;
    }

    /**
     * 登录验证方法
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        log.info("用户：{}，开始验证用户密码", username);

        User user = userService.findUserByName(username);
        if (user == null) {
            log.info("用户：{}，不存在", username);
            return null;
        }

        // 加盐加密的话就需要传四个参数
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getUsername(), user.getPassword(), ByteSource.Util.bytes(user.getSalt()),
                getName());
        log.info("用户:{},密码验证完成", username);
        return authenticationInfo;
    }
}
