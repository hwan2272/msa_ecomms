package com.hwan2272.masecomms.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ResponseProduct {
    private String productId;
    private String productName;
    private Integer unitPrice;
    private String note;
    private Date createdAt;
}
