package com.hansungmarket.demo.controller.board;

import com.hansungmarket.demo.dto.board.BoardImageDto;
import com.hansungmarket.demo.service.board.BoardImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;


@Api(tags = {"이미지"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class BoardImageController {
    private final BoardImageService boardImageService;

    // id로 이미지 출력
    @GetMapping(value = "/images/{id}",
                produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    @ApiOperation(value = "이미지 출력", notes = "해당 id를 가진 이미지 출력")
    public ResponseEntity<byte[]> showBoardImage(@PathVariable Long id) throws IOException {
        BoardImageDto boardImageDto = boardImageService.searchBoardImage(id);
        String imagePath = boardImageDto.getStoredFilePath() + File.separator + boardImageDto.getStoredFileName();

        byte[] byteImage = boardImageService.getByteImage(imagePath);

        return new ResponseEntity<>(byteImage, HttpStatus.OK);
    }

    // 이미지 다운로드
    @GetMapping("/images/{id}/download")
    @ApiOperation(value = "이미지 다운로드", notes = "해당 id를 가진 이미지 다운로드")
    public ResponseEntity<Object> downloadImage(@PathVariable Long id) {
        BoardImageDto boardImageDto = boardImageService.searchBoardImage(id);
        
        try {
            String imagePath = boardImageDto.getStoredFilePath() + File.separator + boardImageDto.getStoredFileName();

            Resource imageResource = boardImageService.getImageResource(imagePath);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentDisposition(
                    ContentDisposition.builder("attachment")
                            .filename(boardImageDto.getOriginalFileName())
                            .build());

            return new ResponseEntity<>(imageResource, httpHeaders, HttpStatus.OK);

        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }

}
