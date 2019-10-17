package com.imdzz.blog.model;

import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import java.util.List;

/**
 * @author imdzz
 * @version 1.0
 * @date 2019/10/16 9:53
 */
@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    // 这个唯一注解是有问题的，只有在jpa帮你创建数据库的时候才生效。
    // 如果你改过或者数据库（库，不是表）不是你创建的，这个注解就无效
    // jpa建好表后自己在数据库添加约束“alter table user add unique(username)”
    @Column(unique = true)
    private String username;
    private String password;
    private String salt;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "uid") },
            inverseJoinColumns = {@JoinColumn(name = "rid") })
    private List<Role> roles;

    public String getCredentialsSalt() {
        return username + salt + salt;
    }
}
