package com.rental_management.service;

import com.rental_management.dto.ResponseBody;
import com.rental_management.entities.UserMessage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MessageService {

    ResponseBody sendUserMessageToOwner(Long ownerId, Long userId, Long ownerMessageId, Long userMessageId, String userMessageContent);
    List<UserMessage> getRepliesToOwnerMessage(Long ownerId);


}
