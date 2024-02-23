package com.hansungmarket.demo.dto.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatRoomDto {
    private Long id;

    private Long partnerId;
    private String partnerNickname;

    @Builder
    private ChatRoomDto(Long id, Long partnerId, String partnerNickname) {
        this.id = id;
        this.partnerId = partnerId;
        this.partnerNickname = partnerNickname;
    }
}
