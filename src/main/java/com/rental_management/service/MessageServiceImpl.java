package com.rental_management.service;

import com.rental_management.dto.ErrorDTO;
import com.rental_management.dto.ResponseBody;
import com.rental_management.dto.SuccessDTO;
import com.rental_management.entities.Owner;
import com.rental_management.entities.OwnerMessage;
import com.rental_management.entities.User;
import com.rental_management.entities.UserMessage;
import com.rental_management.repo.OwnerMessageRepository;
import com.rental_management.repo.OwnerRepository;
import com.rental_management.repo.UserMessageRepository;
import com.rental_management.repo.UserRepository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MessageServiceImpl implements MessageService{

    private final UserMessageRepository userMessageRepository;
    private final OwnerMessageRepository ownerMessageRepository;
    private final OwnerRepository ownerRepository;
    private final UserRepository userRepository;

    public MessageServiceImpl(UserMessageRepository userMessageRepository, OwnerMessageRepository ownerMessageRepository, OwnerRepository ownerRepository, UserRepository userRepository) {
        this.userMessageRepository = userMessageRepository;
        this.ownerMessageRepository = ownerMessageRepository;
        this.ownerRepository = ownerRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseBody sendUserMessageToOwner(Long ownerId, Long userId, Long ownerMessageId, Long userMessageId, String userMessageContent) {
        ResponseBody responseBody = new ResponseBody();
        List<ErrorDTO> errors = new ArrayList<>();
        List<SuccessDTO> successes = new ArrayList<>();

        Optional<Owner> optionalOwner = ownerRepository.findById(ownerId);
        if (optionalOwner.isEmpty()) {
            ErrorDTO error = new ErrorDTO();
            error.setErrors(true);
            error.setMessage("Owner with id: " + ownerId + " not found!");
            errors.add(error);
            responseBody.setError(errors);
            return responseBody;
        }
        Owner existingOwner = optionalOwner.get();

        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            ErrorDTO error = new ErrorDTO();
            error.setErrors(true);
            error.setMessage("User with id: " + userId + " not found");
            errors.add(error);
            responseBody.setError(errors);
            return responseBody;
        }
        User existingUser = optionalUser.get();

        Optional<OwnerMessage> optionalOwnerMessage = ownerMessageRepository.findById(ownerMessageId);
        if (optionalOwnerMessage.isEmpty()) {
            ErrorDTO error = new ErrorDTO();
            error.setErrors(true);
            error.setMessage("Owner message with id: " + userId + " not found");
            errors.add(error);
            responseBody.setError(errors);
            return responseBody;
        }
        OwnerMessage existingOwnerMessage = optionalOwnerMessage.get();

        Optional<UserMessage> optionalUserMessage = userMessageRepository.findById(userMessageId);
        if (optionalUserMessage.isEmpty()) {
            ErrorDTO error = new ErrorDTO();
            error.setErrors(true);
            error.setMessage("User message with id: " + userId + " not found");
            errors.add(error);
            responseBody.setError(errors);
            return responseBody;
        }
        UserMessage existingUserMessage = optionalUserMessage.get();

        existingUserMessage.setContent(userMessageContent);
        existingUserMessage.setTimestamp(Timestamp.from(Instant.now()));
        existingUserMessage.setUser(existingUser);
        existingUserMessage.setReplyToOwnerMessage(existingOwnerMessage);

        userMessageRepository.save(existingUserMessage);
        SuccessDTO successDTO = new SuccessDTO();
        successDTO.setSuccess(true);
        successDTO.setMessage("Message sent successfully");
        successes.add(successDTO);
        responseBody.setSuccess(successes);


        responseBody.setSuccess(successes);
        responseBody.setError(errors);

        return responseBody;
    }

    @Override
    public List<UserMessage> getRepliesToOwnerMessage(Long ownerId) {
        // Fetch the OwnerMessage
        OwnerMessage ownerMessage = ownerMessageRepository.findById(ownerId).get();

        return ownerMessage.getRepliesFromUsers();
    }
}
