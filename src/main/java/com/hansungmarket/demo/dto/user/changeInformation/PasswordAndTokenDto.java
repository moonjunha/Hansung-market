package com.hansungmarket.demo.dto.user.changeInformation;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class PasswordAndTokenDto {
    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;

    @NotBlank(message = "인증번호를 입력하세요.")
    private String authToken;

}
