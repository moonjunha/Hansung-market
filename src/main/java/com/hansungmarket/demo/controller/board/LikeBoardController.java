package com.hansungmarket.demo.controller.board;

import com.hansungmarket.demo.config.auth.PrincipalDetails;
import com.hansungmarket.demo.dto.board.BoardResponseDto;
import com.hansungmarket.demo.service.board.LikeBoardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"게시글 찜 기능"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class LikeBoardController {
    private final LikeBoardService likeBoardService;

    // 게시글 찜하기
    @PostMapping("/likeBoards/{boardId}")
    @ApiOperation(value = "찜하기", notes = "해당 id를 가진 게시글을 사용자의 찜 목록에 추가")
    public Long createLikeBoard(@PathVariable Long boardId, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        return likeBoardService.create(boardId, principalDetails.getUserId());
    }

    // 사용자가 찜한 게시글 출력
    @GetMapping("/likeBoards")
    @ApiOperation(value = "찜한 게시글 출력", notes = "사용자가 찜한 게시글 출력")
    public List<BoardResponseDto> getMyLikeBoards(@RequestParam(defaultValue = "1") int page,
                                                  Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        return likeBoardService.searchByUserId(principalDetails.getUserId(), page);
    }

    // 게시글 찜하기 취소
    @DeleteMapping("/likeBoards/{boardId}")
    @ApiOperation(value = "찜 취소", notes = "해당 id를 가진 게시글을 사용자의 찜 목록에서 제거")
    public void deleteLikeBoard(@PathVariable Long boardId, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        likeBoardService.delete(boardId, principalDetails.getUserId());
    }
}
