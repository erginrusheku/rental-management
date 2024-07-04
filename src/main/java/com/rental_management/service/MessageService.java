package com.rental_management.service;

import com.rental_management.dto.MessageDTO;
import com.rental_management.dto.ResponseBody;

import java.util.List;

public interface MessageService {
    ResponseBody createMessage(Long ownerId, Long userId, List<MessageDTO> messageList);
}
