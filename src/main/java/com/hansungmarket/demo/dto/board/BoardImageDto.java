package com.hansungmarket.demo.dto.board;

import com.hansungmarket.demo.entity.board.BoardImage;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardImageDto {
    private Long id;
    private String originalFileName;
    private String storedFileName;
    private String storedFilePath;

    public BoardImageDto(BoardImage entity) {
        this.id = entity.getId();
        this.originalFileName = entity.getOriginalFileName();
        this.storedFileName = entity.getStoredFileName();
        this.storedFilePath = entity.getStoredFilePath();
    }
}
