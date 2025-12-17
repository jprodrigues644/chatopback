package com.op.chatopback.controller;

import com.op.chatopback.dto.ApiMessageResponse;
import com.op.chatopback.dto.MessageRequest;
import com.op.chatopback.dto.MessageResponse;
import com.op.chatopback.service.MessageService;
import com.op.chatopback.util.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<ApiMessageResponse> sendMessage(
            @RequestBody MessageRequest request,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        messageService.sendMessage(request, customUserDetails.getId());
        return ResponseEntity.ok(new ApiMessageResponse("Message send with success"));
    }
}
