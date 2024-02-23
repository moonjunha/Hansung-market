package com.hansungmarket.demo.controller.board;

import com.hansungmarket.demo.config.auth.PrincipalDetails;
import com.hansungmarket.demo.dto.board.BoardRequestDto;
import com.hansungmarket.demo.dto.board.BoardResponseDto;
import com.hansungmarket.demo.service.board.BoardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Api(tags = {"게시글 생성, 검색, 수정, 삭제"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class BoardController {
    private final BoardService boardService;

    // 게시글 저장
    @PostMapping("/boards")
    @ApiOperation(value = "게시글 저장", notes = "json 에 담겨있는 게시글 정보(BoardRequestDto) + 이미지로 게시글 저장")
    public Long createBoard(@RequestPart(value = "board") @Valid BoardRequestDto requestDto,
                            @RequestPart(value = "images", required = false) List<MultipartFile> images,
                            Authentication authentication) throws IOException {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        return boardService.create(requestDto, images, principalDetails.getUserId());
    }

    // 게시글 목록 출력
    @GetMapping("/boards")
    @ApiOperation(value = "게시글 목록 출력", notes = "게시글 목록 출력, 검색 가능")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sale", value = "전체검색 = 값 X(null), 판매 중 = true, 판매 완료 = false"),
            @ApiImplicitParam(name = "orderType", value = "최신순 = dateDesc, 낮은 가격 순 = priceAsc")
    })
    public List<BoardResponseDto> searchAllBoards(@RequestParam(required = false) String category,
                                                  @RequestParam(required = false) String nickname,
                                                  @RequestParam(required = false) String goodsName,
                                                  @RequestParam(required = false) String title,
                                                  @RequestParam(required = false) Boolean sale,
                                                  @RequestParam(required = false, defaultValue = "dateDesc") String orderType,
                                                  @RequestParam(defaultValue = "1") int page) {
        return boardService.searchByFields(category, nickname, goodsName, title, sale, orderType, page);
    }

    // id에 해당하는 게시글 출력(게시글 상세보기)
    @GetMapping("/boards/{id}")
    @ApiOperation(value = "게시글 상세보기", notes = "id에 해당하는 게시글 출력")
    public BoardResponseDto searchBoardById(@PathVariable Long id, Authentication authentication) {
        // 로그인 X
        if (authentication == null) {
            return boardService.searchByBoardId(id, null);
        }

        // 로그인 O
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        return boardService.searchByBoardId(id, principalDetails.getUserId());
    }

    // 게시글 수정
    @PutMapping("/boards/{id}")
    @ApiOperation(value = "게시글 수정", notes = "json에 담겨있는 게시글 정보(BoardRequestDto)로 해당 id 게시글 수정")
    public Long updateBoard(@PathVariable Long id,
                            @RequestPart(value = "board") BoardRequestDto requestDto,
                            @RequestPart(value = "images", required = false) List<MultipartFile> images,
                            Authentication authentication) throws IOException {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        return boardService.update(id, requestDto, images, principalDetails.getUserId());
    }

    //게시글 삭제
    @DeleteMapping("/boards/{id}")
    @ApiOperation(value = "게시글 삭제", notes = "해당 id 게시글 삭제")
    public void deleteBoard(@PathVariable Long id,
                            Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        boardService.delete(id, principalDetails.getUserId());
    }

    // 사용자가 작성한 게시글 출력
    @GetMapping("/myBoards")
    @ApiOperation(value = "작성한 게시글 출력", notes = "사용자가 작성한 게시글 출력")
    public List<BoardResponseDto> getMyBoards(@RequestParam(defaultValue = "1") int page,
                                              Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        return boardService.searchByFields(null, principalDetails.getNickname(), null, null, null, null, page);
    }

    // 판매완료 설정
    @PatchMapping("/boards/{id}/soldOut")
    @ApiOperation(value = "판매완료 설정", notes = "해당 게시글을 판매완료 상태로 설정")
    public void soldOut(@PathVariable Long id, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        boardService.soldOut(id, principalDetails.getUserId());
    }

    // 판매 중 설정
    @PatchMapping("/boards/{id}/sale")
    @ApiOperation(value = "판매 중 설정", notes = "해당 게시글을 판매 중 상태로 설정")
    public void sale(@PathVariable Long id, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        boardService.sale(id, principalDetails.getUserId());
    }

}
