package com.hansungmarket.demo.repository.likeBoard;

import com.hansungmarket.demo.entity.board.LikeBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeBoardRepository extends JpaRepository<LikeBoard, Long>, LikeBoardRepositoryCustom {
}
