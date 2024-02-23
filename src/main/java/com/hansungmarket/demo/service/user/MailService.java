package com.hansungmarket.demo.service.user;

import com.hansungmarket.demo.dto.user.findAccount.EmailAndUsernameDto;
import com.hansungmarket.demo.dto.user.findAccount.EmailDto;
import com.hansungmarket.demo.entity.user.Role;
import com.hansungmarket.demo.entity.user.User;
import com.hansungmarket.demo.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MailService {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final UserRepository userRepository;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${address}")
    private String address;

    // 인증메일 전송
    @Async
    @Transactional
    public void sendAuthMail(Long userId) throws MessagingException {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        // 랜덤값으로 토큰 생성
        String token = UUID.randomUUID().toString();
        String link = address + "/api/mail/auth/" + token;
        // 토큰 업데이트
        user.setAuthToken(token);

        // 이메일 내용 가져오기
        Context context = new Context();
        context.setVariable("link", link);
        String mailContent = templateEngine.process("authTokenLink", context);

        // 이메일 설정
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        messageHelper.setFrom(fromEmail);
        messageHelper.setTo(user.getEmail());
        messageHelper.setSubject("한성마켓 이메일 인증");
        messageHelper.setText(mailContent, true);

        // 이메일 전송
        mailSender.send(mimeMessage);
    }

    // 계정 인증
    @Transactional
    public void verify(String token) {
        User user = userRepository.findByAuthToken(token).orElseThrow(() -> new IllegalArgumentException("인증 가능한 계정이 존재하지 않습니다."));

        Role role = Role.builder()
                .id(2L) // ROLE_USER 하드코딩, DB 접근 X
                .build();

        // ROLE_USER 권한 설정
        user.setRole(role);
    }

    // username 목록 전송
    @Async
    @Transactional
    public void sendUsernameList(EmailDto emailDto) throws MessagingException {
        String email = emailDto.getEmail();
        List<String> usernameList = userRepository.findUsernameByEmailCustom(email);

        // 이메일 내용 가져오기
        Context context = new Context();
        context.setVariable("usernameList", usernameList);
        String mailContent = templateEngine.process("usernameList", context);

        // 이메일 설정
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        messageHelper.setFrom(fromEmail);
        messageHelper.setTo(email);
        messageHelper.setSubject("한성마켓 아이디 찾기");
        messageHelper.setText(mailContent, true);

        // 이메일 전송
        mailSender.send(mimeMessage);
    }
    
    // 비밀번호 찾기 인증번호 전송
    @Async
    @Transactional
    public void sendFindPasswordToken(EmailAndUsernameDto emailAndUsernameDto) throws MessagingException {
        User user = userRepository.findByUsernameAndEmail(emailAndUsernameDto.getUsername(), emailAndUsernameDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        // 랜덤값으로 토큰 생성
        String findPasswordToken = UUID.randomUUID().toString();
        
        // 토큰 업데이트
        user.setAuthToken(findPasswordToken);
        
        // 이메일 내용 가져오기
        Context context = new Context();
        context.setVariable("findPasswordToken", findPasswordToken);
        String mailContent = templateEngine.process("findPasswordToken", context);

        // 이메일 설정
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        messageHelper.setFrom(fromEmail);
        messageHelper.setTo(emailAndUsernameDto.getEmail());
        messageHelper.setSubject("한성마켓 비밀번호 찾기 인증번호");
        messageHelper.setText(mailContent, true);

        // 이메일 전송
        mailSender.send(mimeMessage);
    }
}
