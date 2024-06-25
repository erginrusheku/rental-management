package com.rental_management.service;

import com.rental_management.dto.MessageDTO;

import java.util.List;

public interface MessageService {
    List<MessageDTO> getAllMessages();
    MessageDTO createMessage(MessageDTO messageDTO);
    MessageDTO getById(Long messageId);
    MessageDTO updateMessage(Long messageId,MessageDTO messageDTO);
    void deleteMessage(Long messageId);
}
