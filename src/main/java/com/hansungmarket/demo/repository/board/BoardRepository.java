package com.hansungmarket.demo.repository.board;

import com.hansungmarket.demo.entity.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {

}
