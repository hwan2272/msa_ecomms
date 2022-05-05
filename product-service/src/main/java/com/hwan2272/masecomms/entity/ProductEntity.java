package com.hwan2272.masecomms.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name="products")
public class ProductEntity {

    @Id
    @GeneratedValue
    private Long seq;

    @Column(nullable = false, unique = true)
    private String productId;

    @Column(nullable = false, unique = true)
    private String productName;

    @Column(nullable = false, unique = true)
    private Integer qty;

    @Column(nullable = false, unique = true)
    private Integer price;

    @Column(nullable = false, unique = true)
    private String note;

    @Column(nullable = false, unique = true)
    private Date createdAt;
}
