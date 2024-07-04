package com.rental_management.service;

import com.rental_management.dto.ResponseBody;
import com.rental_management.dto.UserMessageDTO;

import java.util.List;

public interface UserMessageService {
    List<UserMessageDTO> getAllMessages();
    ResponseBody createMessageByUser(Long userId, List<UserMessageDTO> messages);
    ResponseBody updateMessageByUser(Long userId, Long messageId, List<UserMessageDTO> messages);
    ResponseBody deleteMessage(Long userId, Long messageId);
}
