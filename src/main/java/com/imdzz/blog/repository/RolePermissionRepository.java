package com.imdzz.blog.repository;

import com.imdzz.blog.model.Permission;
import com.imdzz.blog.model.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author imdzz
 * @version 1.0
 * @date 2019/11/25 15:37
 */
public interface RolePermissionRepository extends JpaRepository<RolePermission, String> {

    public List<String> findPidByRid(String rid);

    @Query(value = "select blog.Permission.* from blog.permission, blog.role_permission " +
            " where role_permission.pid = permission.id and role_permission.rid = ?1",
            nativeQuery = true)
    public List<Permission> findPermissionByRid(String rid);
}
