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
    private String id;
    private String username;
    private String password;
    private String salt;

    public String getCredentialsSalt() {
        return username + salt + salt;
    }
}
