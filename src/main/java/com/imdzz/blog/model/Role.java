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
    private String id;
    private String role;
}
