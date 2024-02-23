package com.hansungmarket.demo.dto.board;

import com.hansungmarket.demo.entity.board.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class BoardRequestDto {
    @NotBlank
    private String title;

    @NotBlank
    private String goodsName;

    @NotBlank
    private String goodsCategory;

    @NotBlank
    private String content;

    @NotNull
    private Integer price;

    @Builder
    private BoardRequestDto(String title, String goodsName, String goodsCategory, String content, Integer price) {
        this.title = title;
        this.goodsName = goodsName;
        this.goodsCategory = goodsCategory;
        this.content = content;
        this.price = price;
    }

    public Board toEntity() {
        return Board.builder()
                .title(title)
                .goodsName(goodsName)
                .goodsCategory(goodsCategory)
                .content(content)
                .price(price)
                .build();
    }
}
