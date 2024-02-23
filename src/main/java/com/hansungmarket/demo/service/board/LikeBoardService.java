package com.hansungmarket.demo.service.board;

import com.hansungmarket.demo.dto.board.BoardResponseDto;
import com.hansungmarket.demo.entity.board.Board;
import com.hansungmarket.demo.entity.board.LikeBoard;
import com.hansungmarket.demo.entity.user.User;
import com.hansungmarket.demo.repository.likeBoard.LikeBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LikeBoardService {
    private final LikeBoardRepository likeBoardRepository;

    // 게시글 찜하기
    @Transactional
    public Long create(Long boardId, Long userId) {
        // 게시글
        Board board = Board.builder()
                .id(boardId)
                .build();

        // 현재 사용자
        User user = User.builder()
                .id(userId)
                .build();

        // 찜 정보
        LikeBoard likeBoard = LikeBoard.builder()
                .board(board)
                .user(user)
                .build();

        // 찜 db에 저장
        return likeBoardRepository.save(likeBoard).getId();
    }

    // 찜한 게시글 검색
    @Transactional(readOnly = true)
    public List<BoardResponseDto> searchByUserId(Long id, int page) {
        List<BoardResponseDto> BoardResponseDtos = likeBoardRepository.findByUserIdCustom(id, page).stream()
                .map(BoardResponseDto::new)
                .collect(Collectors.toList());

        // 찜 여부 true로 설정
        for (BoardResponseDto boardResponseDto : BoardResponseDtos) {
            boardResponseDto.like();
        }

        return BoardResponseDtos;
    }
    
    // 찜한 게시글 취소
    @Transactional
    public void delete(Long boardId, Long userId) {
        // 게시글
        Board board = Board.builder()
                .id(boardId)
                .build();

        // 현재 사용자
        User user = User.builder()
                .id(userId)
                .build();

        // 찜 정보
        LikeBoard likeBoard = LikeBoard.builder()
                .board(board)
                .user(user)
                .build();

        likeBoardRepository.delete(likeBoard);
    }
}
