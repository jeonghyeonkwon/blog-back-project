package com.jeonghyeon.blogapi.repository;

import com.jeonghyeon.blogapi.model.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board,Long> {
}
