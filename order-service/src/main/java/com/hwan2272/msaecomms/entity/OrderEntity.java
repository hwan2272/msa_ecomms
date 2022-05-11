package com.hwan2272.msaecomms.entity;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name="orders")
public class OrderEntity {

    @Id
    @GeneratedValue
    private Long seq;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String productId;

    @Column(nullable = false, unique = true)
    private String orderId;

    @Column(nullable = false)
    private Integer qty;

    @Column(nullable = false)
    private Integer totalPrice;

    @Column(nullable = false)
    private Integer unitPrice;

    @Column(nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private Date createdAt;
}
