package com.hansungmarket.demo.controller.etc;

import com.hansungmarket.demo.dto.user.UserDto;
import com.hansungmarket.demo.service.etc.EtcService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = {"기타 기능"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class EtcController {
    private final EtcService etcService;

    // 판매왕 랭킹
    @GetMapping("/saleUser")
    @ApiOperation(value = "판매왕 랭킹", notes = "판매왕 1~5등 출력")
    public List<UserDto> getSaleUser() {
        return etcService.getSaleUserRank();
    }
    
    // 많이 거래된 상품
    @GetMapping("/saleGoods")
    @ApiOperation(value = "많이 거래한 상품", notes = "많이 거래한 상품 5가지")
    public List<String> getSaleGoods() {
        return etcService.getSaleGoodsRank();
    }

    // 많이 찜한 상품
    @GetMapping("/likeGoods")
    @ApiOperation(value = "많이 찜한 상품", notes = "많이 찜한 상품 5가지")
    public List<String> getLikeGoods() {
        return etcService.getLikeGoodsRank();
    }
}
