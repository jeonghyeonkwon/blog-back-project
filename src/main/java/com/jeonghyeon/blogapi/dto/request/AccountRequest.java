package com.jeonghyeon.blogapi.dto.request;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class AccountRequest {
    @ApiModelProperty(example = "유저 아이디")
    private String userId;
    @ApiModelProperty(example = "유저 비밀번호")
    private String userPassword;
    @ApiModelProperty(example = "유저 이름")
    private String userName;

}
