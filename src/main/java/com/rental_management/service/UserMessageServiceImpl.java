package com.rental_management.service;

import com.rental_management.dto.*;
import com.rental_management.entities.User;
import com.rental_management.entities.UserMessage;
import com.rental_management.repo.UserMessageRepository;
import com.rental_management.repo.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
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

                    message.setTimestamp(Timestamp.from(Instant.now()));

                    UserMessage createMessage = userMessageRepository.save(message);

                    createMessage.setUser(optionalUser);
                    optionalUser.getMessageList().add(createMessage);

                    SuccessDTO success = new SuccessDTO();
                    success.setSuccess(true);
                    success.setMessage("The message was created successfully");
                    successes.add(success);
                    responseBody.setSuccess(successes);
                    return createMessage;

                }).filter(Objects::nonNull)
                .collect(Collectors.toList());

        userRepository.save(optionalUser);

        userMessageRepository.saveAll(messageList);

        return responseBody;
    }

    @Override
    public ResponseBody updateMessageByUser(Long userId, Long messageId, List<UserMessageDTO> messages) {
        ResponseBody responseBody =  new ResponseBody();
        List<ErrorDTO> errors = new ArrayList<>();
        List<SuccessDTO> successes = new ArrayList<>();

        Optional<User> existingUser = userRepository.findById(userId);
        if(existingUser.isEmpty()){
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setErrors(true);
            errorDTO.setMessage("User with id: " + userId + " not found");
            errors.add(errorDTO);
            responseBody.setError(errors);
            return  responseBody;
        }

        User optionalUser = existingUser.get();

        Optional<UserMessage> existingMessage = userMessageRepository.findById(messageId);
        if(existingMessage.isEmpty()){
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setErrors(true);
            errorDTO.setMessage("Message with id: " + messageId + " not found");
            errors.add(errorDTO);
            responseBody.setError(errors);
            return  responseBody;
        }

        UserMessage optionalMessage = existingMessage.get();

        List<UserMessage> userMessageList = messages.stream().map(userMessageDTO -> {
           modelMapper.map(userMessageDTO, UserMessage.class);

           if(optionalMessage.getContent() == null){
               ErrorDTO errorDTO = new ErrorDTO();
               errorDTO.setErrors(true);
               errorDTO.setMessage("Message without content cant created");
               errors.add(errorDTO);
               responseBody.setError(errors);
               return null;
           }
           modelMapper.map(userMessageDTO, optionalMessage);
           optionalMessage.setTimestamp(Timestamp.from(Instant.now()));

           UserMessage updatedMessage = userMessageRepository.save(optionalMessage);

           updatedMessage.setUser(optionalUser);
           optionalUser.getMessageList().add(updatedMessage);

           SuccessDTO successDTO = new SuccessDTO();
           successDTO.setSuccess(true);
           successDTO.setMessage("Message was updated successfully");
           successes.add(successDTO);
           responseBody.setSuccess(successes);
           return updatedMessage;
        }

        ).filter(Objects::nonNull).collect(Collectors.toList());

        userRepository.save(optionalUser);

        userMessageRepository.saveAll(userMessageList);

        return responseBody;
    }

    public ResponseBody deleteMessage(Long userId, Long messageId){
        ResponseBody responseBody =  new ResponseBody();
        List<ErrorDTO> errors = new ArrayList<>();
        List<SuccessDTO> successes = new ArrayList<>();

        Optional<User> existingUser = userRepository.findById(userId);
        if(existingUser.isEmpty()){
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setErrors(true);
            errorDTO.setMessage("User with id: " + userId + " not found");
            errors.add(errorDTO);
            responseBody.setError(errors);
            return  responseBody;
        }

        User optionalUser = existingUser.get();

        Optional<UserMessage> existingMessage = userMessageRepository.findById(messageId);
        if(existingMessage.isEmpty()){
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setErrors(true);
            errorDTO.setMessage("Message with id: " + messageId + " not found");
            errors.add(errorDTO);
            responseBody.setError(errors);
            return  responseBody;
        }

        UserMessage optionalMessage = existingMessage.get();

        optionalUser.getMessageList().remove(optionalMessage);

        userMessageRepository.delete(optionalMessage);
        SuccessDTO successDTO = new SuccessDTO();
        successDTO.setSuccess(true);
        successDTO.setMessage("Message was deleted successfully");
        successes.add(successDTO);


        responseBody.setError(errors);
        responseBody.setSuccess(successes);

        return responseBody;

    }


}
