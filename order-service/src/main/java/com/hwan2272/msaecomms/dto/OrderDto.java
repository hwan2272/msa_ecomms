package com.hwan2272.msaecomms.dto;

import lombok.Data;

import java.util.Date;

@Data
public class OrderDto {
    private String orderId;
    private String userId;
    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;
    private Date createdAt;
}
