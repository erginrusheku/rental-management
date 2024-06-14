package com.rental_management.controller;

import com.rental_management.dto.MessageDTO;
import com.rental_management.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/all")
    ResponseEntity<List<MessageDTO>> getAllMessages(){
        List<MessageDTO> messageList = messageService.getAllMessages();
        return new ResponseEntity<>(messageList, HttpStatus.OK);
    }

    @PostMapping("/create")
    ResponseEntity<MessageDTO> createMessage(@RequestBody MessageDTO messageDTO){
        MessageDTO message = messageService.createMessage(messageDTO);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/{messageId}")
    ResponseEntity<MessageDTO> getById(@PathVariable Long messageId){
        MessageDTO messageIds = messageService.getById(messageId);
        return new ResponseEntity<>(messageIds, HttpStatus.OK);
    }

    @PutMapping("/updateMessage/{messageId}")
    ResponseEntity<MessageDTO> updateMessage(@PathVariable Long messageId, @RequestBody MessageDTO messageDTO){
        MessageDTO messageAndId = messageService.updateMessage(messageId, messageDTO);
        return new ResponseEntity<>(messageAndId, HttpStatus.OK);
    }

    @DeleteMapping("/deleteMessageId/{messageId}")
    ResponseEntity<Void> deleteById(@PathVariable Long messageId){
        messageService.deleteMessage(messageId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
