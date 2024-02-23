package com.hansungmarket.demo.controller.chat;

import com.hansungmarket.demo.config.auth.PrincipalDetails;
import com.hansungmarket.demo.dto.chat.ChatMessageRequestDto;
import com.hansungmarket.demo.dto.chat.ChatMessageResponseDto;
import com.hansungmarket.demo.dto.chat.ChatRoomDto;
import com.hansungmarket.demo.service.chat.ChatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(tags = {"채팅"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class ChatController {
    private final ChatService chatService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    
    // 내 채팅방 목록 출력
    @GetMapping("/chatRoom")
    @ApiOperation(value = "채팅방 목록 찾기", notes = "내 채팅방 목록 출력, receiverId로 상대방과의 채팅방 출력")
    public List<ChatRoomDto> searchChatRoom(@RequestParam(required = false) Long receiverId,
                                              Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        if(receiverId == null){
            return chatService.searchMyChatRoom(principalDetails.getUserId());
        }

        ChatRoomDto chatRoomDto = chatService.searchChatRoomByUser(principalDetails.getUserId(), receiverId);
        List<ChatRoomDto> chatRoomDtoList = new ArrayList<>();
        chatRoomDtoList.add(chatRoomDto);

        return chatRoomDtoList;
    }

    // 채팅목록 출력
    @GetMapping("/chatRoom/{chatRoomId}/chatMessage")
    @ApiOperation(value = "채팅방 id로 채팅내역 출력", notes = "채팅방 id로 채팅내역 출력")
    public List<ChatMessageResponseDto> searchChatMessage(@PathVariable Long chatRoomId, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        chatService.deleteChatNotice(principalDetails.getUserId(), chatRoomId);
        return chatService.searchChatMessage(chatRoomId);
    }

    // 채팅 전송
    @MessageMapping("/chat")
    @ApiOperation(value = "채팅 전송", notes = "채팅정보 db 저장, 채팅내용 전송")
    public void sendChatMessage(ChatMessageRequestDto chatMessageRequestDto, Authentication authentication){
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        // 채팅정보 저장
        ChatMessageResponseDto chatMessageResponseDto = chatService.saveChatMessage(principalDetails.getUserId(), chatMessageRequestDto);
        // 알림내역 저장
        chatService.saveChatNotice(chatMessageRequestDto.getReceiverId(), chatMessageRequestDto.getChatRoomId());

        // 메시지 전송
        simpMessagingTemplate.convertAndSend("/topic/" + chatMessageRequestDto.getChatRoomId(),
                chatMessageResponseDto);
    }

    // 채팅알림 수 파라미터로 채팅방
    @GetMapping("/chat/notice")
    @ApiOperation(value = "채팅 알림 수 출력", notes = "전체 채팅 알림, 채팅방 기준 알림")
    public long countNotice(@RequestParam(required = false) Long chatRoomId, Authentication authentication){
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        return chatService.countNotice(principalDetails.getUserId(), chatRoomId);
    }

    // 채팅알림 삭제
    @DeleteMapping("/chat/notice")
    @ApiOperation(value = "채팅 알림 삭제", notes = "전체 채팅 알림, 채팅방 기준 알림")
    public void getNotice(@RequestParam(required = false) Long chatRoomId, Authentication authentication){
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        chatService.deleteChatNotice(principalDetails.getUserId(), chatRoomId);
    }

//    // 웹소켓 테스트
//    @MessageMapping("/test")
//    public void test(ChatMessageRequestDto chatMessageRequestDto, Authentication authentication){
//        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
//        System.out.println(" test websoket test " );
//
//        ChatMessageResponseDto chatMessageResponseDto = ChatMessageResponseDto.builder()
//                .userId(principalDetails.getUserId())
//                .message(chatMessageRequestDto.getMessage())
//                .nickname(principalDetails.getNickname())
//                .build();
//
//        // 메시지 전송
//        simpMessagingTemplate.convertAndSend("/topic/"+ chatMessageRequestDto.getChatRoomId(), chatMessageResponseDto);
//    }
//
//    // 메세지 저장 테스트
//    @PostMapping("/message")
//    public void saveMessage(@RequestBody ChatMessageRequestDto chatMessageRequestDto, Authentication authentication){
//        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
//
//        chatService.saveChatMessage(principalDetails.getUserId(), chatMessageRequestDto);
//        chatService.saveChatNotice(chatMessageRequestDto.getReceiverId(), chatMessageRequestDto.getChatRoomId());
//    }
}
