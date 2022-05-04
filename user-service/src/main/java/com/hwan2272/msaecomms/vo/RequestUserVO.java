package com.hwan2272.msaecomms.vo;

import lombok.Data;

@Data
public class RequestUserVO {

    private String email;

    private String pwd;

    private String encPwd;
}
