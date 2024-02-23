package com.hansungmarket.demo.entity.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "email")
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "introduce")
    private String introduce;

    @Column(name = "auth_token")
    private String authToken;

    @Builder
    private User(Long id, String username, String password, String nickname, String email, Role role, String introduce, Boolean enabled, String authToken) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.role = role;
        this.introduce = introduce;
        this.enabled = enabled;
        this.authToken = authToken;
    }
    
    // 권한 지정
    public void setRole(Role role) {
        this.role = role;
    }

    // 인증토큰 설정
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    // 소개글 설정
    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    // 사용 가능 설정
    public void enable() {
        this.enabled = true;
    }

    // 사용 불가능 설정
    public void disable() {
        this.enabled = false;
    }

}
