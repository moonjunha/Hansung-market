package com.hansungmarket.demo.repository.board;

import com.hansungmarket.demo.dto.board.CountGoods;
import com.hansungmarket.demo.dto.board.CountUser;
import com.hansungmarket.demo.entity.board.Board;

import java.util.List;
import java.util.Optional;

public interface BoardRepositoryCustom {
    Optional<Board> findByIdCustom(Long id);

    List<Board> findByFieldsCustom(String category, String nickname, String goodsName,
                                   String title, Boolean sale, String orderType, int page);

    Optional<Long> findUserIdByIdCustom(Long id);

    void updateSaleCustom(Long id, Boolean sale);

    List<CountUser> findUserIdAndSaleCountDescCustom();

    List<CountGoods> findGoodsNameAndSaleCountDescCustom();

    List<CountGoods> findGoodsNameAndLikeCountDescCustom();
}
