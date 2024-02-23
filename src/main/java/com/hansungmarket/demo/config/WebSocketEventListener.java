package com.hansungmarket.demo.config;

import com.hansungmarket.demo.config.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Component
@RequiredArgsConstructor
public class WebSocketEventListener {
    // 웹소켓 연결 이벤트
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
//        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
//        String test = getNativeHeaderTest(accessor);
//
//        // 유저 세션 정보 가져오기
//        Authentication authentication = (Authentication) event.getUser();
//        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
//        System.out.println(principalDetails.getUserId());
    }


    // 구독 이벤트
    @EventListener
    public void handleWebSocketSubscribeListener(SessionSubscribeEvent event) {

    }

    // 구독 해제 이벤트
    @EventListener
    public void handleWebSocketUnsubscribeListener(SessionUnsubscribeEvent event) {

    }

    // 연결 종료 이벤트
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {

    }

    // 헤더 가져오는 방법(프론트에서 설정한 헤더)
    private String getNativeHeaderTest(StompHeaderAccessor accessor) {
        GenericMessage<?> generic = (GenericMessage<?>) accessor.getHeader(SimpMessageHeaderAccessor.CONNECT_MESSAGE_HEADER);
        if (nonNull(generic)) {
            SimpMessageHeaderAccessor nativeAccessor = SimpMessageHeaderAccessor.wrap(generic);
            List<String> test = nativeAccessor.getNativeHeader("test");

            return isNull(test) ? null : test.stream().findFirst().orElse(null);
        }

        return null;
    }
}
