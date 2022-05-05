package com.hwan2272.msaecomms.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDto {

    private Long userSeq;
    private String userId;
    private String encPwd;
}
