package com.imdzz.blog.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @author imdzz
 * @version 1.0
 * @date 2019/10/16 9:56
 */
@Data
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String role;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_permission", joinColumns = { @JoinColumn(name = "rid") },
            inverseJoinColumns = {@JoinColumn(name = "pid") })
    private List<Permission> permissions;
    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "rid") },
            inverseJoinColumns = {@JoinColumn(name = "uid") })
    private List<User> users;
}
