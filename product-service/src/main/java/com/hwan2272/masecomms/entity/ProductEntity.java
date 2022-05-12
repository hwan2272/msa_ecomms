package com.hwan2272.masecomms.entity;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

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

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private Integer unitStock;

    @Column(nullable = false)
    private Integer unitPrice;

    @Column(nullable = false)
    private String note;

    @Column(nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private Date createdAt;
}
