package com.hansungmarket.demo.controller.user;

import com.hansungmarket.demo.dto.user.SignUpDto;
import com.hansungmarket.demo.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = {"회원가입"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class SignUpController {
    private final UserService userService;

    // 회원가입
    @PostMapping("/signUp")
    @ApiOperation(value = "회원가입", notes = "json에 담겨있는 회원가입 정보(SignUpDto) 저장")
    public Long signUp(@RequestBody @Valid SignUpDto signUpDto) {
        return userService.signUp(signUpDto);
    }

    // username 중복검사
    @GetMapping("/signUp/checkUsername/{username}")
    @ApiOperation(value = "username 중복검사", notes = "동일한 username이 존재하면 false 반환")
    public Boolean checkUsername(@PathVariable String username) {
        return userService.checkDuplicateUsername(username);
    }

    // nickname 중복검사
    @GetMapping("/signUp/checkNickname/{nickname}")
    @ApiOperation(value = "nickname 중복검사", notes = "동일한 nickname이 존재하면 false 반환")
    public Boolean checkNickname(@PathVariable String nickname) {
        return userService.checkDuplicateNickname(nickname);
    }
}
