package com.imdzz.blog.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author imdzz
 * @version 1.0
 * @date 2019/11/25 15:27
 */
@Entity
@Data
public class RolePermission {
    @Id
    String id;
    String rid; // 角色id
    String pid; // 权限id
}
