package com.hansungmarket.demo.entity.board;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hansungmarket.demo.entity.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "board")
public class Board extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "goods_name")
    private String goodsName;

    @Column(name = "goods_category")
    private String goodsCategory;

    @Column(name = "price")
    private Integer price;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @Column(name = "sale")
    private Boolean sale;

    @OneToMany(mappedBy = "board", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<BoardImage> boardImages = new ArrayList<>();

    @Builder
    private Board(Long id, String title, String goodsName, String goodsCategory,
                  Integer price, String content, User user, Boolean sale) {
        this.id = id;
        this.title = title;
        this.goodsName = goodsName;
        this.goodsCategory = goodsCategory;
        this.price = price;
        this.content = content;
        this.user = user;
        this.sale = sale;
    }

    // 게시글 업데이트
    public void update(String title, String goodsName, String goodsCategory, Integer price, String content) {
        this.title = title;
        this.goodsName = goodsName;
        this.goodsCategory = goodsCategory;
        this.price = price;
        this.content = content;
    }

}
