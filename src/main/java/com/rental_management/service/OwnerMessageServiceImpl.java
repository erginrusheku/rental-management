package com.rental_management.service;

import com.rental_management.dto.*;

import com.rental_management.entities.Owner;
import com.rental_management.entities.OwnerMessage;
import com.rental_management.repo.OwnerMessageRepository;
import com.rental_management.repo.OwnerRepository;
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
public class OwnerMessageServiceImpl implements OwnerMessageService {

   private final OwnerRepository ownerRepository;
   private final OwnerMessageRepository messageRepository;
    private final ModelMapper modelMapper;

    public OwnerMessageServiceImpl(OwnerRepository ownerRepository, OwnerMessageRepository messageRepository, ModelMapper modelMapper) {
        this.ownerRepository = ownerRepository;
        this.messageRepository = messageRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<OwnerMessageDTO> getAllMessages() {
        return List.of();
    }

    @Override
    public OwnerMessageDTO createMessage(OwnerMessageDTO messageDTO) {
        return null;
    }

    @Override
    public OwnerMessageDTO getById(Long messageId) {
        return null;
    }

    @Override
    public OwnerMessageDTO updateMessage(Long messageId, OwnerMessageDTO messageDTO) {
        return null;
    }

    @Override
    public void deleteMessage(Long messageId) {

    }
    @Override
    public ResponseBody createMessageByOwner(Long ownerId, List<OwnerMessageDTO> messageList){
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

        List<OwnerMessage> messages = messageList.stream().map(messageDTO -> {

            if(messageDTO.getContent() == null){
                ErrorDTO errorDTO = new ErrorDTO();
                errorDTO.setErrors(true);
                errorDTO.setMessage("You can't continue without content");
                errorList.add(errorDTO);
                responseBody.setError(errorList);
                return null;
            }

            OwnerMessage message = modelMapper.map(messageDTO, OwnerMessage.class);

            message.setTimestamp(Timestamp.from(Instant.now()));
            OwnerMessage createMessage = messageRepository.save(message);
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
