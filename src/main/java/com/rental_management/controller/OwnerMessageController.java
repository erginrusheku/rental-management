package com.rental_management.controller;

import com.rental_management.dto.OwnerMessageDTO;
import com.rental_management.dto.ResponseBody;
import com.rental_management.service.OwnerMessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ownerMessages")
public class OwnerMessageController {

    private final OwnerMessageService messageService;

    public OwnerMessageController(OwnerMessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/all")
    ResponseEntity<List<OwnerMessageDTO>> getAllMessages(){
        List<OwnerMessageDTO> messageList = messageService.getAllMessages();
        return new ResponseEntity<>(messageList, HttpStatus.OK);
    }

    @PostMapping("/create")
    ResponseEntity<OwnerMessageDTO> createMessage(@RequestBody OwnerMessageDTO messageDTO){
        OwnerMessageDTO message = messageService.createMessage(messageDTO);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/messageId/{messageId}")
    ResponseEntity<OwnerMessageDTO> getById(@PathVariable Long messageId){
        OwnerMessageDTO messageIds = messageService.getById(messageId);
        return new ResponseEntity<>(messageIds, HttpStatus.OK);
    }

    @PutMapping("/updateMessage/{messageId}")
    ResponseEntity<OwnerMessageDTO> updateMessage(@PathVariable Long messageId, @RequestBody OwnerMessageDTO messageDTO){
        OwnerMessageDTO messageAndId = messageService.updateMessage(messageId, messageDTO);
        return new ResponseEntity<>(messageAndId, HttpStatus.OK);
    }

    @DeleteMapping("/deleteMessageId/{messageId}")
    ResponseEntity<Void> deleteById(@PathVariable Long messageId){
        messageService.deleteMessage(messageId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/createMessage/{ownerId}")
    ResponseEntity<ResponseBody> createMessageByOwner(@PathVariable Long ownerId, @RequestBody List<OwnerMessageDTO> messageList){
        ResponseBody createMessage = messageService.createMessageByOwner(ownerId, messageList);
        return new ResponseEntity<>(createMessage, HttpStatus.CREATED);
    }
}
