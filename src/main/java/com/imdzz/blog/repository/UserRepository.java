package com.imdzz.blog.repository;

import com.imdzz.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author imdzz
 * @version 1.0
 * @date 2019/10/16 10:28
 */
public interface UserRepository extends JpaRepository<User, String> {

    public List<User> findUserByUsername(String username);
}
