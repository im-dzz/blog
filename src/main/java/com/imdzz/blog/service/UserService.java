package com.imdzz.blog.service;

import com.imdzz.blog.enums.ErrorCodeEnum;
import com.imdzz.blog.exception.BlogException;
import com.imdzz.blog.model.Permission;
import com.imdzz.blog.model.Role;
import com.imdzz.blog.model.User;
import com.imdzz.blog.repository.RolePermissionRepository;
import com.imdzz.blog.repository.RoleRepository;
import com.imdzz.blog.repository.UserRepository;
import com.imdzz.blog.repository.UserRoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author imdzz
 * @version 1.0
 * @date 2019/10/16 10:28
 */
@Service
@Slf4j
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRoleRepository userRoleRepository;
    @Autowired
    RolePermissionRepository rolePermissionRepository;

    public User findUserByName(String username){
        List<User> userList = userRepository.findUserByUsername(username);

        if (userList == null || userList.size() < 1){
            throw new BlogException(ErrorCodeEnum.USER_NOT_EXITS);
        } else if (userList.size() > 1){
            throw new BlogException((ErrorCodeEnum.TOO_MUCH_USERS));
        }

        return userList.get(0);
    }

    /**
     * 新建用户，默认角色为普通用户
     * @param username
     * @param password
     * @return
     */
//    public User registUser(String username, String password){
//        User user = new User();
//        user.setUsername(username);
//        user.setPassword(password);
//        // 5-游客
//        Optional<Role> role = roleRepository.findById(5);
//        List roles = user.getRoles();
//        if(roles == null){
//            roles = new ArrayList<>(3);
//        }
//        roles.add(role.get());
//        user.setRoles(roles);
//        userRepository.save(user);
//        return user;
 //   }

    /**
     * 查找某用户的所有角色
     * @param userId
     * @return
     */
    public List<String> getUserRoles(String userId){
        List<String> roleList = userRoleRepository.findRidByUid(userId);
        return roleList;
    }

    /**
     * 查找某用户的所有权限
     * @param userId
     * @return
     */
    public List<String> getUserPermissions(String userId){
        List<String> permissionList = new ArrayList<>();
        List<String> ridList = getUserRoles(userId);

        for (String rid : ridList){
            permissionList.addAll(rolePermissionRepository.findPidByRid(rid));
        }
        return permissionList;
    }
}
