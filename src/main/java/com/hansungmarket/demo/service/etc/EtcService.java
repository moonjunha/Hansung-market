package com.hansungmarket.demo.service.etc;

import com.hansungmarket.demo.dto.board.CountGoods;
import com.hansungmarket.demo.dto.board.CountUser;
import com.hansungmarket.demo.dto.user.UserDto;
import com.hansungmarket.demo.repository.board.BoardRepository;
import com.hansungmarket.demo.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class EtcService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    // 판매 유저 랭킹
    // 1~5등까지
    @Transactional(readOnly = true)
    public List<UserDto> getSaleUserRank() {
        List<CountUser> countUserList = boardRepository.findUserIdAndSaleCountDescCustom();

        // user id 리스트
        List<Long> ids = new ArrayList<>();
        for (CountUser countUser : countUserList) {
            ids.add(countUser.getUserId());
        }

        // DB 검색결과
        List<UserDto> userDtoList = userRepository.findUserDtoListByIdCustom(ids);

        // 판매순 정렬
        List<UserDto> orderedUserDtoList = new ArrayList<>();
        for (Long id : ids) {
            for (UserDto userDto : userDtoList) {
                if (Objects.equals(id, userDto.getId())) {
                    orderedUserDtoList.add(userDto);
                    break;
                }
            }
        }

        return orderedUserDtoList;
    }

    // 판매 상품 랭킹
    // 1~5등까지
    @Transactional(readOnly = true)
    public List<String> getSaleGoodsRank() {
        List<CountGoods> countGoodsList = boardRepository.findGoodsNameAndSaleCountDescCustom();
        
        // 상품명 리스트
        List<String> goodsNameList = new ArrayList<>();
        for (CountGoods countGoods : countGoodsList) {
            goodsNameList.add(countGoods.getGoodsName());
        }
        
        return goodsNameList;
    }

    // 찜 상품 랭킹
    // 1~5등까지
    @Transactional(readOnly = true)
    public List<String> getLikeGoodsRank() {
        List<CountGoods> countGoodsList = boardRepository.findGoodsNameAndLikeCountDescCustom();

        // 상품명 리스트
        List<String> goodsNameList = new ArrayList<>();
        for (CountGoods countGoods : countGoodsList) {
            goodsNameList.add(countGoods.getGoodsName());
        }

        return goodsNameList;
    }
}
