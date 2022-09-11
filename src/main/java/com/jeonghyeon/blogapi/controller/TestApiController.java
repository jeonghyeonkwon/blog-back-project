package com.jeonghyeon.blogapi.controller;

import com.jeonghyeon.blogapi.dto.request.AccountRequest;
import com.jeonghyeon.blogapi.dto.request.LoginRequest;
import com.jeonghyeon.blogapi.security.jwt.TokenProvider;
import com.jeonghyeon.blogapi.security.util.SecurityUtil;
import com.jeonghyeon.blogapi.service.TestService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class TestApiController {
    private final TestService testService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;

    @PostMapping({"","/"})
    public ResponseEntity reigster(@RequestBody AccountRequest dto){
        Long savedId = testService.register(dto);
        if(savedId==null) return new ResponseEntity("이미 가입된 유저 입니다.",HttpStatus.BAD_REQUEST);
        return new ResponseEntity(savedId, HttpStatus.CREATED);
    }

    @GetMapping("/init")
    public ResponseEntity createAdmin(){
        testService.createAdmin();
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest dto){

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(dto.getUsername(),dto.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = tokenProvider.createToken(authentication);

        return new ResponseEntity(jwtToken,HttpStatus.OK);
    }
    @GetMapping("/login-check")
    public ResponseEntity userInfo(){
        Optional<String> opAccountId = SecurityUtil.getCurrnetAccountId();
        if(opAccountId.isPresent()){
            return new ResponseEntity(opAccountId.get(),HttpStatus.OK);
        }
        return new ResponseEntity("로그인 실패",HttpStatus.BAD_REQUEST);
    }

}
