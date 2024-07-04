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

    @GetMapping("/messageId")
    ResponseEntity<OwnerMessageDTO> getById(@RequestParam Long messageId){
        OwnerMessageDTO messageIds = messageService.getById(messageId);
        return new ResponseEntity<>(messageIds, HttpStatus.OK);
    }

    @PutMapping("/updateMessage")
    public ResponseEntity<ResponseBody> updateMessageByOwner(@RequestParam Long ownerId, @RequestParam Long messageId, @RequestBody List<OwnerMessageDTO> messageDTO) {
        ResponseBody messageUpdated = messageService.updateMessageByOwner(ownerId, messageId, messageDTO);
        return new ResponseEntity<>(messageUpdated, HttpStatus.OK);
    }

    @PostMapping("/createMessage")
    ResponseEntity<ResponseBody> createMessageByOwner(@RequestParam Long ownerId, @RequestBody List<OwnerMessageDTO> messageList){
        ResponseBody createMessage = messageService.createMessageByOwner(ownerId, messageList);
        return new ResponseEntity<>(createMessage, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteMessage")
    public ResponseEntity<ResponseBody> deleteOwnerMessage(@RequestParam Long ownerId,@RequestParam Long messageId){
        ResponseBody deleteMessage = messageService.deleteOwnerMessage(ownerId, messageId);
        return new ResponseEntity<>(deleteMessage, HttpStatus.OK);
    }

}
