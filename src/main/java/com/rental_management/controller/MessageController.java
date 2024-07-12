package com.rental_management.controller;

import com.rental_management.dto.MessageDTO;
import com.rental_management.dto.ResponseBody;
import com.rental_management.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/createMessage/{ownerId}/{userId}")
    public ResponseEntity<ResponseBody> createMessage(@PathVariable Long ownerId, @PathVariable Long userId, @RequestBody List<MessageDTO> messageList) {
        ResponseBody createMessage = messageService.createMessage(ownerId, userId, messageList);
        return new ResponseEntity<>(createMessage, HttpStatus.CREATED);
    }

    @PutMapping("/updateMessage/{ownerId}/{userId}")
    public ResponseEntity<ResponseBody> updateMessage(@PathVariable Long ownerId, @PathVariable Long userId, @RequestParam Long messageId, @RequestBody List<MessageDTO> messageList) {
        ResponseBody updateMessage = messageService.updateMessage(ownerId, userId, messageId, messageList);
        return new ResponseEntity<>(updateMessage, HttpStatus.OK);
    }

    @DeleteMapping("/deleteMessage/{messageId}")
    ResponseEntity<ResponseBody> deleteMessage(@PathVariable Long messageId) {
        ResponseBody deleteMessage = messageService.deleteMessage(messageId);
        return new ResponseEntity<>(deleteMessage, HttpStatus.OK);
    }
}
