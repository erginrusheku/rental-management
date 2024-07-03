package com.rental_management.service;


import com.rental_management.dto.OwnerMessageDTO;
import com.rental_management.dto.ResponseBody;


import java.util.List;

public interface OwnerMessageService {
    List<OwnerMessageDTO> getAllMessages();
    OwnerMessageDTO createMessage(OwnerMessageDTO messageDTO);
    OwnerMessageDTO getById(Long messageId);
    void deleteMessage(Long messageId);
    ResponseBody createMessageByOwner(Long ownerId, List<OwnerMessageDTO> messageList);
    ResponseBody updateMessageByOwner(Long ownerId, Long messageId,List<OwnerMessageDTO> messageDTO);
    ResponseBody deleteOwnerMessage(Long ownerId, Long messageId);
}
