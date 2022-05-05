package com.hwan2272.msaecomms.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name="orders")
public class OrderEntity {

    @Id
    @GeneratedValue
    private Long seq;

    @Column(nullable = false, unique = true)
    private Long userSeq;

    @Column(nullable = false, unique = true)
    private Long productSeq;

    @Column(nullable = false, unique = true)
    private Long qty;

    @Column(nullable = false, unique = true)
    private Date createdAt;
}
