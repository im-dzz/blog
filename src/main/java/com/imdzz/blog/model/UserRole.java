package com.imdzz.blog.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author imdzz
 * @version 1.0
 * @date 2019/11/25 15:12
 */
@Data
@Entity
public class UserRole {
    @Id
    String id;
    String uid; // 用户id
    String rid; // 角色id
}
