package com.jeonghyeon.blogapi.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class BoardListResponse {
    private Long id;
    private String title;
    private String writer;
    private String createdDate;
    private Long view;

    public BoardListResponse(Long id, String title, String writer, LocalDateTime localDateTime, Long view){
        this.id = id;
        this.title = title;
        this.writer = writer;
        String createdDate = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.createdDate = createdDate;
        this.view = view;

    }
}
