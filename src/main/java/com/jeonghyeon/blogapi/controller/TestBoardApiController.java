package com.jeonghyeon.blogapi.controller;

import com.jeonghyeon.blogapi.dto.request.BoardRequest;
import com.jeonghyeon.blogapi.security.util.SecurityUtil;
import com.jeonghyeon.blogapi.service.TestService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test/board")
public class TestBoardApiController {
    private final TestService testService;


    @PostMapping("")
    @ApiOperation(value = "게시글 작성", notes = "게시글 작성하기 위한 api")
    public ResponseEntity createBoard(@RequestBody BoardRequest dto){
        Optional<String> opAccountId = SecurityUtil.getCurrentAccountId();
        if(opAccountId.isEmpty()) throw new IllegalStateException("로그인 후 이용해 주세요.");
        String accountId = opAccountId.get();
        Long boardId = testService.createBoard(accountId,dto);

        return new ResponseEntity(boardId,HttpStatus.CREATED);
    }
}
