package com.hansungmarket.demo.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"로그인, 로그아웃"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class LoginController {
    @PostMapping("/login")
    @ApiOperation(value = "로그인", notes = "form 으로 username, password를 전송하면 로그인 수행(json X)")
    public void doLogin(@RequestPart String username, @RequestPart String password) {}

    @PostMapping("/logout")
    @ApiOperation(value = "로그아웃", notes = "로그아웃 수행")
    public void doLogout() {}

    @GetMapping(value = "/login", produces = "application/json; charset=utf8")
    @ApiOperation(value = "로그인 메시지", notes = "로그인하지 않은 상태로 특정 api에 접근하는 경우 403코드와 메시지 반환")
    public ResponseEntity<String> noLogin() {
        return new ResponseEntity<>("로그인이 필요합니다.", HttpStatus.FORBIDDEN);
    }

    @GetMapping(value = "/login/success", produces = "application/json; charset=utf8")
    @ApiOperation(value = "로그인 성공 메시지", notes = "로그인에 성공하면 200코드와 메시지 반환")
    public ResponseEntity<String> successLogin() {
        return new ResponseEntity<>("로그인 성공", HttpStatus.OK);
    }

    @GetMapping(value = "/login/fail", produces = "application/json; charset=utf8")
    @ApiOperation(value = "로그인 실패 메시지", notes = "로그인에 실패하면 401코드와 메시지 반환")
    public ResponseEntity<String> failLogin() {
        return new ResponseEntity<>("로그인 실패. 아이디와 비밀번호를 확인하세요", HttpStatus.UNAUTHORIZED);
    }

    @GetMapping(value = "/logout/success", produces = "application/json; charset=utf8")
    @ApiOperation(value = "로그아웃 성공 메시지", notes = "로그아웃 성공하면 200코드와 메시지 반환")
    public ResponseEntity<String> successLogout() {
        return new ResponseEntity<>("로그아웃 성공", HttpStatus.OK);
    }
}
