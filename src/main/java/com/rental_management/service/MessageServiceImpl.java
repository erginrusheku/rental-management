package com.rental_management.service;

import com.rental_management.dto.MessageDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Override
    public List<MessageDTO> getAllMessages() {
        return List.of();
    }

    @Override
    public MessageDTO createMessage(MessageDTO messageDTO) {
        return null;
    }

    @Override
    public MessageDTO getById(Long messageId) {
        return null;
    }

    @Override
    public MessageDTO updateMessage(Long messageId, MessageDTO messageDTO) {
        return null;
    }

    @Override
    public void deleteMessage(Long messageId) {

    }
}
