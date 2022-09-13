package com.jeonghyeon.blogapi.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class LoginRequest {
    @ApiModelProperty(example = "유저 아이디")
    private String username;
    @ApiModelProperty(example = "유저 비밀번호")
    private String password;
}
