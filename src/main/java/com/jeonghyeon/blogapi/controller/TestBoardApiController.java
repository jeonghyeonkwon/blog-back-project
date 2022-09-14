package com.jeonghyeon.blogapi.controller;

import com.jeonghyeon.blogapi.dto.PageFrame;
import com.jeonghyeon.blogapi.dto.request.BoardRequest;
import com.jeonghyeon.blogapi.dto.response.BoardDetailResponse;
import com.jeonghyeon.blogapi.security.util.SecurityUtil;
import com.jeonghyeon.blogapi.service.TestService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test/board")
public class TestBoardApiController {
    private final TestService testService;


    @GetMapping("")
    @ApiOperation(value = "게시글 리스트", notes = "게시글 리스트 api")
    public ResponseEntity boardList(@PageableDefault(size = 10, direction = Sort.Direction.DESC,sort = "id")Pageable pageable){
        PageFrame pageFrame = testService.boardList(pageable);
        return new ResponseEntity(pageFrame, HttpStatus.OK);
    }

    @PostMapping("")
    @ApiOperation(value = "게시글 작성", notes = "게시글 작성하기 위한 api")
    public ResponseEntity createBoard(@RequestBody BoardRequest dto){
        Optional<String> opAccountId = SecurityUtil.getCurrentAccountId();
        if(opAccountId.isEmpty()) throw new IllegalStateException("로그인 후 이용해 주세요.");
        String accountId = opAccountId.get();
        Long boardId = testService.createBoard(accountId,dto);

        return new ResponseEntity(boardId,HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "게시글 자세히", notes = "게시글 자세히 api")
    public ResponseEntity boardDetail(@PathVariable Long id){
        BoardDetailResponse boardDetailResponse = testService.boardDetail(id);
        return new ResponseEntity(boardDetailResponse,HttpStatus.OK);
    }
}
