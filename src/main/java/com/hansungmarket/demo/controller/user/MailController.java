package com.hansungmarket.demo.controller.user;

import com.hansungmarket.demo.config.auth.PrincipalDetails;
import com.hansungmarket.demo.dto.user.findAccount.EmailAndUsernameDto;
import com.hansungmarket.demo.dto.user.findAccount.EmailDto;
import com.hansungmarket.demo.service.user.MailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@Api(tags = {"메일인증"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class MailController {
    private final MailService mailService;

    // 인증메일 보내기
    @PostMapping("/mail/auth")
    @ApiOperation(value = "인증메일 보내기", notes = "인증 토큰이 담긴 메일을 사용자의 이메일 주소로 발송")
    public void sendAuthMail(Authentication authentication) throws MessagingException {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        mailService.sendAuthMail(principalDetails.getUserId());
    }

    // 토큰 확인
    @GetMapping("/mail/auth/{token}")
    @ApiOperation(value = "인증메일 확인", notes = "메일로 인증하면 계정 권한 상승(글쓰기 등 가능)")
    public String verifyToken(@PathVariable String token) {
        mailService.verify(token);
        return "인증 완료";
    }

    // username 목록 메일 보내기
    @PostMapping("/mail/usernames")
    @ApiOperation(value = "아이디 목록 전송", notes = "해당 이메일로 가입한 아이디 목록 전송")
    public void sendUsernameList(@RequestBody EmailDto emailDto) throws MessagingException {
        mailService.sendUsernameList(emailDto);
    }

    // 비밀번호 찾기 인증번호 메일 보내기
    @PostMapping("/mail/findPasswordToken")
    @ApiOperation(value = "비밀번호 찾기 인증토큰 보내기", notes = "해당 이메일로 비밀번호 찾기에 쓰는 인증토큰 전송")
    public void sendFindPasswordToken(@RequestBody EmailAndUsernameDto emailAndUsernameDto) throws MessagingException {
        mailService.sendFindPasswordToken(emailAndUsernameDto);
    }
}
