package com.hwan2272.msaecomms.dto;

import com.hwan2272.msaecomms.vo.ResponseOrder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserDto {
    private String email;
    private String name;
    private String pwd;
    private String userId;
    private Date createdAt;

    private String decPwd;

    private String encPwd;

    private List<ResponseOrder> orders;
}
