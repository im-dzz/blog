package com.imdzz.blog.service;

import com.imdzz.blog.model.Role;
import com.imdzz.blog.model.User;
import com.imdzz.blog.repository.RoleRepository;
import com.imdzz.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author imdzz
 * @version 1.0
 * @date 2019/10/16 10:28
 */
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    public User findUserByName(String username){
        return userRepository.findByUsername(username);
    }

    /**
     * 新建用户，默认角色为普通用户
     * @param username
     * @param password
     * @return
     */
    public User registUser(String username, String password){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        // 5-游客
        Optional<Role> role = roleRepository.findById(5);
        List roles = user.getRoles();
        if(roles == null){
            roles = new ArrayList<>(3);
        }
        roles.add(role.get());
        user.setRoles(roles);
        userRepository.save(user);
        return user;
    }
}
