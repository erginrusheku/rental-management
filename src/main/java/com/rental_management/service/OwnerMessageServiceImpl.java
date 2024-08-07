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
    private final OwnerMessageRepository ownerMessageRepository;

    public OwnerMessageServiceImpl(OwnerRepository ownerRepository, OwnerMessageRepository messageRepository, ModelMapper modelMapper, OwnerMessageRepository ownerMessageRepository) {
        this.ownerRepository = ownerRepository;
        this.messageRepository = messageRepository;
        this.modelMapper = modelMapper;
        this.ownerMessageRepository = ownerMessageRepository;
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
            createMessage.setOwner(optionalOwner);
            optionalOwner.getMessageList().add(createMessage);

            SuccessDTO successDTO = new SuccessDTO();
            successDTO.setSuccess(true);
            successDTO.setMessage("The message was created successfully");
            successList.add(successDTO);
            responseBody.setSuccess(successList);
            return createMessage;

        }).filter(Objects::nonNull).collect(Collectors.toList());

        //optionalOwner.setMessageList(messages);
         ownerRepository.save(optionalOwner);

        //messages.forEach(message -> message.setOwner(saveOwner));
        messageRepository.saveAll(messages);

       //modelMapper.map(saveOwner, OwnerDTO.class);

        return responseBody;
    }

    @Override
    public ResponseBody updateMessageByOwner(Long ownerId, Long messageId, List<OwnerMessageDTO> messageList) {
        ResponseBody responseBody = new ResponseBody();
        List<ErrorDTO> errors = new ArrayList<>();
        List<SuccessDTO> successes = new ArrayList<>();

        Optional<Owner> existingOwner = ownerRepository.findById(ownerId);
        if(existingOwner.isEmpty()){
            ErrorDTO error = new ErrorDTO();
            error.setErrors(true);
            error.setMessage("You can't update the message without an owner id");
            errors.add(error);
            responseBody.setError(errors);
            return responseBody;

        }

        Owner optionalOwner = existingOwner.get();

        Optional<OwnerMessage> existingMessageOptional = ownerMessageRepository.findById(messageId);
        if(existingMessageOptional.isEmpty()){
            ErrorDTO error = new ErrorDTO();
            error.setErrors(true);
            error.setMessage("Message not found with id " + messageId);
            errors.add(error);
            responseBody.setError(errors);
            return responseBody;
        }

        OwnerMessage existingMessage = existingMessageOptional.get();

        List<OwnerMessage> messages = messageList.stream().map(messageDTO -> {
            if(messageDTO.getContent() == null){
                ErrorDTO error = new ErrorDTO();
                error.setErrors(true);
                error.setMessage("Message couldn't be updated with new content");
                errors.add(error);
                responseBody.setError(errors);
                return null;
        }

            modelMapper.map(messageDTO, OwnerMessage.class);

            existingMessage.setTimestamp(Timestamp.from(Instant.now()));

            modelMapper.map(messageDTO, existingMessage);

            OwnerMessage createMessage = ownerMessageRepository.save(existingMessage);
            createMessage.setOwner(optionalOwner);
            optionalOwner.getMessageList().add(createMessage);

            SuccessDTO success = new SuccessDTO();
            success.setSuccess(true);
            success.setMessage("Message updated successfully");
            successes.add(success);
            responseBody.setSuccess(successes);
            return createMessage;


        }).filter(Objects::nonNull).collect(Collectors.toList());

        //optionalOwner.setMessageList(messages);
         ownerRepository.save(optionalOwner);

        //messages.forEach(message -> message.setOwner(savedOwner));
         messageRepository.saveAll(messages);

        return responseBody;
    }

    @Override
    public ResponseBody deleteOwnerMessage(Long ownerId, Long messageId) {
        ResponseBody responseBody = new ResponseBody();
        List<ErrorDTO> errors = new ArrayList<>();
        List<SuccessDTO> successes = new ArrayList<>();

        Optional<Owner> optionalOwner = ownerRepository.findById(ownerId);
        if(optionalOwner.isEmpty()){
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setErrors(true);
            errorDTO.setMessage("Owner with id: "+ ownerId+" not found");
            errors.add(errorDTO);
            responseBody.setError(errors);
            return responseBody;
        }

        Owner existingOwner = optionalOwner.get();

        Optional<OwnerMessage> optionalOwnerMessage = messageRepository.findById(messageId);
        if(optionalOwnerMessage.isEmpty()){
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setErrors(true);
            errorDTO.setMessage("Message with id: "+ messageId +" not found");
            errors.add(errorDTO);
            responseBody.setError(errors);
            return responseBody;
        }
        OwnerMessage existingMessage = optionalOwnerMessage.get();

        existingOwner.getMessageList().remove(existingMessage);

        messageRepository.deleteById(existingMessage.getMessageId());
        SuccessDTO successDTO = new SuccessDTO();
        successDTO.setSuccess(true);
        successDTO.setMessage("Message was deleted successfully");
        successes.add(successDTO);
        responseBody.setSuccess(successes);

        return responseBody;
    }


}
