package com.hansungmarket.demo.service.user;

import com.hansungmarket.demo.dto.user.SignUpDto;
import com.hansungmarket.demo.dto.user.UserDto;
import com.hansungmarket.demo.dto.user.changeInformation.PasswordAndTokenDto;
import com.hansungmarket.demo.entity.user.Role;
import com.hansungmarket.demo.entity.user.User;
import com.hansungmarket.demo.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    
    // 회원가입
    // form 으로 받는 경우 코드 수정 필요, 현재 json 으로 데이터 받음
    @Transactional
    public Long signUp(SignUpDto signUpDto) {
        // 비밀번호 암호화
        String encodedPassword = bCryptPasswordEncoder.encode(signUpDto.getPassword());
        Role role = Role.builder()
                .id(1L) // ROLE_NOT_VERIFIED 하드코딩, DB 접근 X
                .build();

        User user = User.builder()
                .username(signUpDto.getUsername())
                .password(encodedPassword)
                .nickname(signUpDto.getUsername())
                .email(signUpDto.getEmail())
                .role(role)
                .enabled(true)
                .build();

        // 유저 정보 저장(회원가입)
        userRepository.save(user);

        return user.getId();
    }

    // 동일 username 존재하면 false 반환
    @Transactional(readOnly = true)
    public Boolean checkDuplicateUsername(String username) {
        return !userRepository.existByUsernameCustom(username);
    }

    // 동일 nickname 존재하면 false 반환
    @Transactional(readOnly = true)
    public Boolean checkDuplicateNickname(String nickname) {
        return !userRepository.existByNicknameCustom(nickname);
    }

    // 사용자 정보 반환
    @Transactional(readOnly = true)
    public UserDto getUserInfo(Long id) {
        return userRepository.findUserDtoByIdCustom(id);
    }

    // 소개글 업데이트
    @Transactional
    public void updateIntroduce(Long id, String introduce) {
        userRepository.updateIntroduceByIdCustom(id, introduce);
    }

    // 비밀번호 업데이트
    @Transactional
    public void updatePassword(Long id, String password) {
        // 비밀번호 암호화
        String encodedPassword = bCryptPasswordEncoder.encode(password);

        userRepository.updatePasswordByIdCustom(id, encodedPassword);
    }

    // 비밀번호 변경
    @Transactional
    public void changePassword(PasswordAndTokenDto passwordAndTokenDto) {
        // 비밀번호 암호화
        String encodedPassword = bCryptPasswordEncoder.encode(passwordAndTokenDto.getPassword());

        userRepository.updatePasswordByAuthTokenCustom(passwordAndTokenDto.getAuthToken(), encodedPassword);
    }
}
