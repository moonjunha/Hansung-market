package com.hansungmarket.demo.dto.board;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CountGoods {
    private String goodsName;
    private Long saleOrLikeCount;
}
