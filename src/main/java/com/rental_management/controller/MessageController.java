package com.rental_management.controller;

import com.rental_management.dto.ResponseBody;
import com.rental_management.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/sendUserMessage")
    public ResponseEntity<ResponseBody> sendUserMessageToOwner(@RequestParam Long ownerId, @RequestParam Long userId, @RequestParam Long ownerMessageId, @RequestParam Long userMessageId, @RequestBody String userMessageContent){
        ResponseBody sendUserMessage = messageService.sendUserMessageToOwner(ownerId, userId, ownerMessageId, userMessageId, userMessageContent);
        return new ResponseEntity<>(sendUserMessage, HttpStatus.OK);
    }

}
