package com.hwan2272.msaecomms.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name="users")
public class UserEntity {

    @Id
    @GeneratedValue
    private Long seq;

    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false, unique = true)
    private String encPwd;

    @Column(nullable = false, unique = true)
    private Date createdAt;

}
