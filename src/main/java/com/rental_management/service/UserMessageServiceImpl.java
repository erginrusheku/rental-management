package com.rental_management.service;

import com.rental_management.dto.*;
import com.rental_management.entities.User;
import com.rental_management.entities.UserMessage;
import com.rental_management.repo.UserMessageRepository;
import com.rental_management.repo.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class UserMessageServiceImpl implements UserMessageService{

    private final UserMessageRepository userMessageRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserMessageServiceImpl(UserMessageRepository userMessageRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.userMessageRepository = userMessageRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<UserMessageDTO> getAllMessages() {
        return null;
    }

    @Override
    public UserMessageDTO createMessage(UserMessageDTO userMessageDTO) {
        return null;
    }

    @Override
    public UserMessageDTO getById(Long messageId) {
        return null;
    }

    @Override
    public UserMessageDTO updateMessage(Long messageId, UserMessageDTO messageDTO) {
        return null;
    }

    @Override
    public void deleteMessage(Long messageId) {

    }

    @Override
    public ResponseBody createMessageByUser(Long userId, List<UserMessageDTO> messages) {
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

        List<UserMessage> messageList = messages.stream().map(messageDTO -> {
                    if(messageDTO.getContent() == null){
                        ErrorDTO error = new ErrorDTO();
                        error.setErrors(true);
                        error.setMessage("You can't countiue without content");
                        errors.add(error);
                        responseBody.setError(errors);
                        return null;
                    }

                    UserMessage message = modelMapper.map(messageDTO, UserMessage.class);
                    UserMessage createMessage = userMessageRepository.save(message);
                    SuccessDTO success = new SuccessDTO();
                    success.setSuccess(true);
                    success.setMessage("The message was created successfully");
                    successes.add(success);
                    responseBody.setSuccess(successes);
                    return createMessage;

                }).filter(Objects::nonNull)
                .collect(Collectors.toList());

        optionalUser.setMessageList(messageList);
        User savedUser = userRepository.save(optionalUser);

        messageList.forEach(message -> message.setUser(savedUser));
        userMessageRepository.saveAll(messageList);

        modelMapper.map(savedUser, UserDTO.class);

        return responseBody;
    }



}
