package com.rental_management.controller;

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
    @GetMapping("/messageId")
    ResponseEntity<UserMessageDTO> getById(@RequestParam Long messageId){
        UserMessageDTO messageIds = userMessageService.getById(messageId);
        return new ResponseEntity<>(messageIds, HttpStatus.OK);
    }
    @PostMapping("/createMessage")
    public ResponseEntity<ResponseBody> createMessageByUser(@RequestParam Long userId, @RequestBody List<UserMessageDTO> messages ) {
        ResponseBody userMessageDTO = userMessageService.createMessageByUser(userId, messages);
        return new ResponseEntity<>(userMessageDTO, HttpStatus.CREATED);
    }
    @PutMapping("/updateMessage")
    public ResponseEntity<ResponseBody> updateMessageByOwner(@RequestParam Long userId, @RequestParam Long messageId, @RequestBody List<UserMessageDTO> messageList){
        ResponseBody updateMessage = userMessageService.updateMessageByUser(userId, messageId, messageList);
        return new ResponseEntity<>(updateMessage, HttpStatus.OK);
    }
    @DeleteMapping("/deleteMessage")
    ResponseEntity<ResponseBody> deleteMessage(@RequestParam Long userId,@RequestParam Long messageId){
      ResponseBody deleteMessage = userMessageService.deleteMessage(userId, messageId);
      return new ResponseEntity<>(deleteMessage, HttpStatus.OK);
    }
}
