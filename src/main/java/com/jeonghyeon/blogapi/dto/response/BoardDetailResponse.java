package com.jeonghyeon.blogapi.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class BoardDetailResponse {
    private String title;
    private String content;
    private String writer;
    private Long view;
    private String createdDate;
    public BoardDetailResponse(String title, String content, String writer, LocalDateTime localDateTime, Long view){
        this.title = title;
        this.content = content;
        this.writer = writer;
        String createdDate = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd Hh:mm"));
        this.createdDate = createdDate;
        this.view = view;

    }
}
