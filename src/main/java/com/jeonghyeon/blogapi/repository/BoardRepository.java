package com.jeonghyeon.blogapi.repository;

import com.jeonghyeon.blogapi.dto.response.BoardDetailResponse;
import com.jeonghyeon.blogapi.dto.response.BoardListResponse;
import com.jeonghyeon.blogapi.model.board.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board,Long> {

    // BoardListResponse(Long id, String title, String writer, LocalDateTime localDateTime, Long view)
    @Query("SELECT new com.jeonghyeon.blogapi.dto.response.BoardListResponse(board.id, board.title, account.userId, board.createdDate, board.view)" +
            " FROM Board board" +
            " JOIN board.account account")
    Page<BoardListResponse> boardList(Pageable pageable);

//      BoardDetailResponse(String title, String content, String writer, LocalDateTime localDateTime, Long view)
    @Query("SELECT new com.jeonghyeon.blogapi.dto.response.BoardDetailResponse(board.title, board.content, account.userId, board.createdDate, board.view)" +
            " FROM Board board" +
            " JOIN board.account account" +
            " WHERE board.id = :id")
    Optional<BoardDetailResponse> boardDetail(Long id);


    @Query("SELECT board" +
            " FROM Board board" +
            " JOIN FETCH board.account" +
            " WHERE board.id = :id")
    Optional<Board> findById(Long id);
}
