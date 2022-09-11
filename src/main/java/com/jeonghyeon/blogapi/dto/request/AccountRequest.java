package com.jeonghyeon.blogapi.dto.request;


import lombok.Data;


@Data
public class AccountRequest {
    private String userId;
    private String userPassword;
    private String userName;

}
