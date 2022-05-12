package com.hwan2272.masecomms.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ProductDto {
    private String productId;
    private String productName;
    private Integer unitPrice;
    private String note;
    private Date createdAt;
}
