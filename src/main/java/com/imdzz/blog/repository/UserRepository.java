package com.imdzz.blog.repository;

import com.imdzz.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author imdzz
 * @version 1.0
 * @date 2019/10/16 10:28
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByUsername(String username);
}
