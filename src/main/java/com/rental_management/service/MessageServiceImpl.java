package com.rental_management.service;

import com.rental_management.dto.*;
import com.rental_management.entities.Message;
import com.rental_management.entities.Owner;
import com.rental_management.repo.MessageRepository;
import com.rental_management.repo.OwnerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

   private final OwnerRepository ownerRepository;
   private final MessageRepository messageRepository;
    private final ModelMapper modelMapper;

    public MessageServiceImpl(OwnerRepository ownerRepository, MessageRepository messageRepository, ModelMapper modelMapper) {
        this.ownerRepository = ownerRepository;
        this.messageRepository = messageRepository;
        this.modelMapper = modelMapper;
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
    public ResponseBody createMessageByOwner(Long ownerId, List<MessageDTO> messageList){
        ResponseBody responseBody = new ResponseBody();
        List<ErrorDTO> errorList = new ArrayList<>();
        List<SuccessDTO> successList = new ArrayList<>();

        Optional<Owner> existingOwner = ownerRepository.findById(ownerId);
        if(existingOwner.isEmpty()){
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setErrors(true);
            errorDTO.setMessage("You can't create a message without an owner");
            errorList.add(errorDTO);
            responseBody.setError(errorList);
            return responseBody;
        }

        Owner optionalOwner = existingOwner.get();

        List<Message> messages = messageList.stream().map(messageDTO -> {

            if(messageDTO.getContent() == null){
                ErrorDTO errorDTO = new ErrorDTO();
                errorDTO.setErrors(true);
                errorDTO.setMessage("You can't continue without content");
                errorList.add(errorDTO);
                responseBody.setError(errorList);
                return null;
            }

            Message message = modelMapper.map(messageDTO, Message.class);
            Message createMessage = messageRepository.save(message);
            SuccessDTO successDTO = new SuccessDTO();
            successDTO.setSuccess(true);
            successDTO.setMessage("The message was created successfully");
            successList.add(successDTO);
            responseBody.setSuccess(successList);
            return createMessage;

        }).filter(Objects::nonNull).collect(Collectors.toList());

        optionalOwner.setMessageList(messages);
        Owner saveOwner = ownerRepository.save(optionalOwner);

        messages.forEach(message -> message.setOwner(saveOwner));
        messageRepository.saveAll(messages);

        modelMapper.map(saveOwner, OwnerDTO.class);

        return responseBody;
    }
}
