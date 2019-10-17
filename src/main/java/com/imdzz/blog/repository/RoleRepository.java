package com.imdzz.blog.repository;

import com.imdzz.blog.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author imdzz
 * @version 1.0
 * @date 2019/10/16 15:21
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {
    
}
