package com.imdzz.blog.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @author imdzz
 * @version 1.0
 * @date 2019/10/16 10:03
 */
@Data
@Entity
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ManyToMany
    @JoinTable(name = "role_permission", joinColumns = { @JoinColumn(name = "pid") }, inverseJoinColumns = {
            @JoinColumn(name = "rid") })
    private List<Role> roles;
}
