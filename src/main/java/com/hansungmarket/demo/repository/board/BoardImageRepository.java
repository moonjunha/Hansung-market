package com.hansungmarket.demo.repository.board;

import com.hansungmarket.demo.entity.board.BoardImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardImageRepository extends JpaRepository<BoardImage, Long> {
    List<BoardImage> findByBoardId(Long id);

    void deleteByBoardId(Long id);
}
