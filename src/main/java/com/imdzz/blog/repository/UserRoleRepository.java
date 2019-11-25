package com.imdzz.blog.repository;

import com.imdzz.blog.model.Role;
import com.imdzz.blog.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author imdzz
 * @version 1.0
 * @date 2019/11/25 15:34
 */
public interface UserRoleRepository extends JpaRepository<UserRole, String> {
    public List<String> findRidByUid(String uid);

    @Query(value = "select blog.role.* from blog.user_role, blog.role where user_role.rid = role.id and user_role.uid = ?1",
            nativeQuery = true)
    public List<Role> findRolesByUid(String uid);
}
