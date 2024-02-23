package com.hansungmarket.demo.dto.user;

import com.hansungmarket.demo.entity.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
public class SignUpDto {
    @NotBlank(message = "아이디를 입력하세요.")
    private String username;

    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;

    @NotBlank(message = "닉네임을 입력하세요.")
    private String nickname;

    @NotBlank
    @Pattern(regexp="[A-Za-z0-9]{1,20}[@]hansung.ac.kr", message = "한성대학교 웹메일만 입력할 수 있습니다.")
    private String email;

//    @Builder
//    private SignUpDto(String username, String password, String nickname, String email) {
//        this.username = username;
//        this.password = password;
//        this.nickname = nickname;
//        this.email = email;
//    }

    public User toEntity() {
        return User.builder()
                .username(username)
                .password(password)
                .nickname(nickname)
                .email(email)
                .build();
    }
}
