package com.rental_management.controller;


import com.rental_management.dto.OwnerMessageDTO;
import com.rental_management.dto.ResponseBody;
import com.rental_management.dto.UserMessageDTO;
import com.rental_management.service.UserMessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/userMessages")
public class UserMessageController {
    private final UserMessageService userMessageService;

    public UserMessageController(UserMessageService userMessageService) {
        this.userMessageService = userMessageService;
    }

    @GetMapping("/all")
    ResponseEntity<List<UserMessageDTO>> getAllMessages(){
        List<UserMessageDTO> messageList = userMessageService.getAllMessages();
        return new ResponseEntity<>(messageList, HttpStatus.OK);
    }

    @PostMapping("/create")
    ResponseEntity<UserMessageDTO> createMessage(@RequestBody UserMessageDTO userMessageDTO){
        UserMessageDTO message = userMessageService.createMessage(userMessageDTO);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/messageId/{messageId}")
    ResponseEntity<UserMessageDTO> getById(@PathVariable Long messageId){
        UserMessageDTO messageIds = userMessageService.getById(messageId);
        return new ResponseEntity<>(messageIds, HttpStatus.OK);
    }

    @PutMapping("/updateMessage/{messageId}")
    ResponseEntity<UserMessageDTO> updateMessage(@PathVariable Long messageId, @RequestBody UserMessageDTO messageDTO){
        UserMessageDTO messageAndId = userMessageService.updateMessage(messageId, messageDTO);
        return new ResponseEntity<>(messageAndId, HttpStatus.OK);
    }

    @DeleteMapping("/deleteMessageId/{messageId}")
    ResponseEntity<Void> deleteById(@PathVariable Long messageId){
        userMessageService.deleteMessage(messageId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/createMessage/{userId}")
    public ResponseEntity<ResponseBody> createMessageByUser(@PathVariable Long userId, @RequestBody List<UserMessageDTO> messages) {
        ResponseBody userMessageDTO = userMessageService.createMessageByUser(userId, messages);
        return new ResponseEntity<>(userMessageDTO, HttpStatus.CREATED);
    }

    @PutMapping("/updateMessage")
    public ResponseEntity<ResponseBody> updateMessageByOwner(@RequestParam Long userId, @RequestParam Long messageId, @RequestBody List<UserMessageDTO> messageList){
        ResponseBody updateMessage = userMessageService.updateMessageByUser(userId, messageId, messageList);
        return new ResponseEntity<>(updateMessage, HttpStatus.OK);
    }
}
