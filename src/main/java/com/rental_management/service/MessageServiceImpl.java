package com.rental_management.service;

import com.rental_management.dto.ErrorDTO;
import com.rental_management.dto.MessageDTO;
import com.rental_management.dto.ResponseBody;
import com.rental_management.dto.SuccessDTO;
import com.rental_management.entities.Message;
import com.rental_management.entities.Owner;
import com.rental_management.entities.User;
import com.rental_management.repo.MessageRepository;
import com.rental_management.repo.OwnerRepository;
import com.rental_management.repo.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService{

    private  final MessageRepository messageRepository;
    private final OwnerRepository ownerRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public MessageServiceImpl(MessageRepository messageRepository, OwnerRepository ownerRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.messageRepository = messageRepository;
        this.ownerRepository = ownerRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseBody createMessage(Long ownerId, Long userId, List<MessageDTO> messageList) {
        ResponseBody responseBody = new ResponseBody();
        List<ErrorDTO> errors = new ArrayList<>();
        List<SuccessDTO> successes = new ArrayList<>();

        Optional<Owner> optionalOwner = ownerRepository.findById(ownerId);
        if(optionalOwner.isEmpty()){
            ErrorDTO error = new ErrorDTO();
            error.setErrors(true);
            error.setMessage("Owner id not found");
            errors.add(error);
            responseBody.setError(errors);
            return responseBody;

        }

        Owner existingOwner = optionalOwner.get();

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

        List<Message> messages = messageList.stream().map(messageDTO -> {

            Message message = modelMapper.map(messageDTO,Message.class);

            Message createMessage = messageRepository.save(message);

            createMessage.setUserMessage(messageDTO.getUserMessage());
            createMessage.setOwnerMessage(messageDTO.getOwnerMessage());

            existingOwner.getOwnerMessage().add(createMessage);
            optionalUser.getUserMessage().add(createMessage);

            createMessage.setUser(optionalUser);
            createMessage.setOwner(existingOwner);

            if(createMessage.getMessageId() == null){
                ErrorDTO errorDTO = new ErrorDTO();
                errorDTO.setErrors(true);
                errorDTO.setMessage("Id null");
                errors.add(errorDTO);
                responseBody.setError(errors);
                return null;
            }

            SuccessDTO successDTO = new SuccessDTO();
            successDTO.setSuccess(true);
            successDTO.setMessage("Message created successfully");
            successes.add(successDTO);
            responseBody.setSuccess(successes);
            return createMessage;

        }).filter(Objects::nonNull).collect(Collectors.toList());

        ownerRepository.save(existingOwner);
        userRepository.save(optionalUser);

        messageRepository.saveAll(messages);

        responseBody.setError(errors);
        responseBody.setSuccess(successes);

        return responseBody;
    }
}