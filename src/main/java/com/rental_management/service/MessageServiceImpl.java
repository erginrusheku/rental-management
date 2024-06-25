package com.rental_management.service;

import com.rental_management.dto.ErrorDTO;
import com.rental_management.dto.MessageDTO;
import com.rental_management.dto.ResponseBody;
import com.rental_management.dto.SuccessDTO;
import com.rental_management.entities.Message;
import com.rental_management.entities.User;
import com.rental_management.repo.MessageRepository;
import com.rental_management.repo.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final MessageRepository messageRepository;

    public MessageServiceImpl(UserRepository userRepository, ModelMapper modelMapper,
                              MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.messageRepository = messageRepository;
    }

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

    @Override
    public ResponseBody createMessageByUser(Long userId, List<MessageDTO> messages) {
        ResponseBody responseBody = new ResponseBody();
        List<ErrorDTO> errors = new ArrayList<>();
        List<SuccessDTO> successes = new ArrayList<>();

        Optional<User> existingUser = userRepository.findById(userId);
        if (existingUser.isEmpty()) {
            ErrorDTO error = new ErrorDTO();
            error.setErrors(true);
            error.setMessage("You can't create a message without a User");
            errors.add(error);
            responseBody.setError(errors);
            return responseBody;
        }

        User optionalUser = existingUser.get();

        List<Message> messageList = messages.stream().map(messageDTO -> {
            if(messageDTO.getContent() == null){
                ErrorDTO error = new ErrorDTO();
                error.setErrors(true);
                error.setMessage("You can't countiue without content");
                errors.add(error);
                responseBody.setError(errors);
                return null;
            }

            Message message = modelMapper.map(messageDTO, Message.class);
            Message createMessage = messageRepository.save(message);
            SuccessDTO success = new SuccessDTO();
            success.setSuccess(true);
            success.setMessage("The message was created successfully");
            successes.add(success);
            responseBody.setSuccess(successes);
            return createMessage;

        }).filter(Objects::nonNull)
                .collect(Collectors.toList());


        return responseBody;
    }
}
