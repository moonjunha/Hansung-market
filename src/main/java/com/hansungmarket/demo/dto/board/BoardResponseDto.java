package com.hansungmarket.demo.dto.board;

import com.hansungmarket.demo.entity.board.Board;
import com.hansungmarket.demo.entity.board.BoardImage;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class BoardResponseDto {
    private Long id;

    private Long writerId;

    private String writer;

    private String title;

    private String goodsName;

    private String goodsCategory;

    private Integer price;

    private String content;

    private LocalDateTime createdDateTime;

    private LocalDateTime modifiedDateTime;

    private List<BoardImage> boardImages;

    private Boolean sale;

    private Boolean liked = false;

    public BoardResponseDto(Board entity) {
        this.id = entity.getId();
        this.writerId = entity.getUser().getId();
        this.writer = entity.getUser().getNickname();
        this.title = entity.getTitle();
        this.goodsName = entity.getGoodsName();
        this.goodsCategory = entity.getGoodsCategory();
        this.price = entity.getPrice();
        this.content = entity.getContent();
        this.createdDateTime = entity.getCreatedDateTime();
        this.modifiedDateTime = entity.getModifiedDateTime();
        this.boardImages = entity.getBoardImages();
        this.sale = entity.getSale();
    }

    public void like() {
        this.liked = true;
    }

}
