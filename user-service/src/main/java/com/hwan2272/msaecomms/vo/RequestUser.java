package com.hwan2272.msaecomms.vo;

import lombok.Data;

@Data
public class RequestUser {

    private String email;
    private String pwd;
    private String encPwd;
}
