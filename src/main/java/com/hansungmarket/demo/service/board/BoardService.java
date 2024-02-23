package com.hansungmarket.demo.service.board;

import com.hansungmarket.demo.dto.board.BoardRequestDto;
import com.hansungmarket.demo.dto.board.BoardResponseDto;
import com.hansungmarket.demo.entity.board.Board;
import com.hansungmarket.demo.entity.board.BoardImage;
import com.hansungmarket.demo.entity.user.User;
import com.hansungmarket.demo.repository.board.BoardRepository;
import com.hansungmarket.demo.repository.likeBoard.LikeBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardImageService boardImageService;
    private final BoardRepository boardRepository;
    private final LikeBoardRepository likeBoardRepository;

    // board id로 게시글 검색
    @Transactional(readOnly = true)
    public BoardResponseDto searchByBoardId(Long boardId, Long userId) {
        Board board = boardRepository.findByIdCustom(boardId).orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        BoardResponseDto boardResponseDto = new BoardResponseDto(board);

        // 로그인 X
        if (userId == null) {
            return boardResponseDto;
        }

        // 로그인 O
        // 찜 내역에 현재 게시글이 존재하면
        if (likeBoardRepository.existByBoardIdAndUserIdCustom(boardId, userId)) {
            // 찜 내역 보여주기
            boardResponseDto.like();
        }

        return boardResponseDto;
    }

    // 동적 쿼리로 게시글 검색
    @Transactional(readOnly = true)
    public List<BoardResponseDto> searchByFields(String category, String nickname, String goodsName,
                                                 String title, Boolean sale, String orderType, int page) {
        return boardRepository.findByFieldsCustom(category, nickname, goodsName,
                        title, sale, orderType, page).stream()
                .map(BoardResponseDto::new)
                .collect(Collectors.toList());
    }

    // 게시글 생성
    @Transactional
    public Long create(BoardRequestDto requestDto, List<MultipartFile> images, Long userId) throws IOException {
        // 사용자
        User user = User.builder()
                .id(userId)
                .build();

        // 게시글
        Board board = Board.builder()
                .title(requestDto.getTitle())
                .goodsName(requestDto.getGoodsName())
                .goodsCategory(requestDto.getGoodsCategory())
                .price(requestDto.getPrice())
                .content(requestDto.getContent())
                .user(user)
                .sale(true)
                .build();
        
        // 게시글 저장
        boardRepository.save(board);

        // 이미지가 없는 경우
        if (CollectionUtils.isEmpty(images)) {
            return board.getId();
        }

        // 이미지가 있는 경우
        for (MultipartFile image : images) {
            // 이미지 저장
            boardImageService.create(board, image);
        }

        return board.getId();
    }

    // 게시글 수정
    @Transactional
    public Long update(Long boardId, BoardRequestDto requestDto, List<MultipartFile> images, Long userId) throws IOException {
        Board board = boardRepository.findByIdCustom(boardId).orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        List<BoardImage> boardImages = board.getBoardImages();

        // 현재 사용자와 게시글 작성자가 다른 경우
        if (!Objects.equals(userId, board.getUser().getId())) {
            throw new RuntimeException("작성자가 일치하지 않습니다.");
        }

        // 수정 전 게시글에 이미지 존재하면 전부 삭제
        if (!CollectionUtils.isEmpty(boardImages)) {
            // 이미지 파일 삭제
            for (BoardImage image : boardImages) {
                boardImageService.deleteFile(image);
            }

            // entity 이미지 목록 지우면 db에 반영(Dirty Checking)
            board.getBoardImages().clear();
        }

        // 수정 후 게시글에 이미지 존재하면 삽입
        if (!CollectionUtils.isEmpty(images)) {
            for (MultipartFile image : images) {
                boardImageService.create(board, image);
            }
        }

        // 게시글 수정사항 업데이트
        board.update(requestDto.getTitle(),
                requestDto.getGoodsName(),
                requestDto.getGoodsCategory(),
                requestDto.getPrice(),
                requestDto.getContent());

        return board.getId();
    }

    // 게시글 삭제
    @Transactional
    public void delete(Long boardId, Long userId) {
        Board board = boardRepository.findByIdCustom(boardId).orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        List<BoardImage> boardImages = board.getBoardImages();

        // 현재 사용자와 게시글 작성자가 다른 경우
        if (!Objects.equals(userId, board.getUser().getId())) {
            throw new RuntimeException("작성자가 일치하지 않습니다.");
        }

        // 게시글에 이미지가 없는 경우
        if (CollectionUtils.isEmpty(boardImages)) {
            // 게시글 삭제
            boardRepository.deleteById(boardId);
            return;
        }

        //게시글에 이미지가 있는 경우
        for (BoardImage boardImage : boardImages) {
            // 이미지 파일 삭제
            boardImageService.deleteFile(boardImage);
        }

        // 게시글 삭제
        boardRepository.deleteById(boardId);
    }

    // 판매완료 설정
    @Transactional
    public void soldOut(Long boardId, Long userId) {
        Long userIdFromBoard = boardRepository.findUserIdByIdCustom(boardId).orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        // 현재 사용자와 게시글 작성자가 다른 경우
        if (!Objects.equals(userId, userIdFromBoard)) {
            throw new RuntimeException("작성자가 일치하지 않습니다.");
        }

        boardRepository.updateSaleCustom(boardId, false);
    }

    // 판매 중 설정
    @Transactional
    public void sale(Long boardId, Long userId) {
        Long userIdFromBoard = boardRepository.findUserIdByIdCustom(boardId).orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        // 현재 사용자와 게시글 작성자가 다른 경우
        if (!Objects.equals(userId, userIdFromBoard)) {
            throw new RuntimeException("작성자가 일치하지 않습니다.");
        }

        boardRepository.updateSaleCustom(boardId, true);
    }
}
