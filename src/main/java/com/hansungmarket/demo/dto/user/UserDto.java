package com.hansungmarket.demo.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String nickname;
    private String username;
    private String email;
    private String introduce;

//    @Builder
//    private UserDto(String nickname, String username, String email, String introduce) {
//        this.nickname = nickname;
//        this.username = username;
//        this.email = email;
//        this.introduce = introduce;
//    }
}
