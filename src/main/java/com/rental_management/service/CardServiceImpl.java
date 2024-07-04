package com.rental_management.service;

import com.rental_management.dto.*;
import com.rental_management.entities.Card;
import com.rental_management.entities.User;
import com.rental_management.repo.CardRepository;
import com.rental_management.repo.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CardServiceImpl implements CardService{

    private final UserRepository userRepository;
    private final CardRepository cardRepository;
    private final ModelMapper modelMapper;

    public CardServiceImpl(UserRepository userRepository,
                           CardRepository cardRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CardDTO getCardById(Long id) {
        return null;
    }

    @Override
    public List<CardDTO> getAllCards() {
        return null;
    }

    @Override
    public ResponseBody createCardByUser(Long userId, List<CardDTO> cardDTOList) {
        ResponseBody responseBody = new ResponseBody();
        List<ErrorDTO> errors = new ArrayList<>();
        List<SuccessDTO> successes = new ArrayList<>();

        Optional<User> existingUser = userRepository.findById(userId);
        if (existingUser.isEmpty()) {
            ErrorDTO error = new ErrorDTO();
            error.setErrors(true);
            error.setMessage("User with id: " + userId + " doesn't exist");
            errors.add(error);
            responseBody.setError(errors);
            return responseBody;
        }

        User user = existingUser.get();

        List<Card> createdCards = cardDTOList.stream()
                .map(cardDTO -> {
                    if(cardRepository.existsByCardNumber(cardDTO.getCardNumber())){
                        ErrorDTO errorDTO = new ErrorDTO();
                        errorDTO.setErrors(true);
                        errorDTO.setMessage("The same card number cannot be used twice");
                        errors.add(errorDTO);
                        responseBody.setError(errors);
                        return null;
                    }
                    else {
                        Card card = modelMapper.map(cardDTO, Card.class);

                        card.setCreationDate(Date.from(Instant.now()));

                        LocalDate cardCreationDate = card.getCreationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        LocalDate cardExpirationDate = cardCreationDate.plusYears(3);

                        Instant instant = cardExpirationDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
                        card.setExpirationDate(Date.from(instant));

                        Card createdCard = cardRepository.save(card);
                        createdCard.setUser(user);
                        user.getCards().add(createdCard);

                        String userName = user.getUserName();
                        card.setCardholderName(userName);
                        SuccessDTO success = new SuccessDTO();
                        success.setSuccess(true);
                        success.setMessage("Card created successfully!");
                        successes.add(success);
                        return createdCard;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        userRepository.save(user);

        cardRepository.saveAll(createdCards);

        responseBody.setError(errors);
        responseBody.setSuccess(successes);

        modelMapper.map(user, UserDTO.class);

        return responseBody;
    }

    @Override
    public ResponseBody updateCardByUser(Long userId, Long cardId, List<CardDTO> cardList) {
        ResponseBody responseBody = new ResponseBody();
        List<ErrorDTO> errors = new ArrayList<>();
        List<SuccessDTO> successes = new ArrayList<>();

        Optional<User> existingUser = userRepository.findById(userId);
        if(existingUser.isEmpty()){
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setErrors(true);
            errorDTO.setMessage("User with id: " + userId + " not found");
            errors.add(errorDTO);
            responseBody.setError(errors);
            return responseBody;
        }

        User optionalUser = existingUser.get();

        Optional<Card> existingCardOpt = cardRepository.findById(cardId);
        if (existingCardOpt.isEmpty()) {
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setErrors(true);
            errorDTO.setMessage("Card with id: " + cardId + " not found");
            errors.add(errorDTO);
            responseBody.setError(errors);
            return responseBody;
        }

        Card existingCard = existingCardOpt.get();


        List<Card> cards = cardList.stream().map(cardDTO1 -> {

                    if(cardRepository.existsByCardNumber(cardDTO1.getCardNumber()) || cardRepository.existsByCardType(cardDTO1.getCardType())){
                        ErrorDTO errorDTO = new ErrorDTO();
                        errorDTO.setErrors(true);
                        errorDTO.setMessage("The same card number and card type cannot be used twice");
                        errors.add(errorDTO);
                        responseBody.setError(errors);
                        return null;
                    }

                    existingCard.setCreationDate(Date.from(Instant.now()));

            LocalDate cardCreationDate = existingCard.getCreationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate cardExpirationDate = cardCreationDate.plusYears(3);
            Instant instant = cardExpirationDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
            existingCard.setExpirationDate(Date.from(instant));

            modelMapper.map(cardDTO1,existingCard);

            Card savedCard = cardRepository.save(existingCard);
            savedCard.setUser(optionalUser);
            optionalUser.getCards().add(savedCard);


            SuccessDTO successDTO = new SuccessDTO();
            successDTO.setSuccess(true);
            successDTO.setMessage("Card updated successfully");
            successes.add(successDTO);

            return savedCard;

        }).filter(Objects::nonNull)
                .collect(Collectors.toList());


        userRepository.save(optionalUser);
        cardRepository.saveAll(cards);


        responseBody.setError(errors);
        responseBody.setSuccess(successes);

      return responseBody;

    }

    @Override
    public ResponseBody deleteCardByUser(Long userId, Long cardId) {
        ResponseBody responseBody = new ResponseBody();
        List<ErrorDTO> errors = new ArrayList<>();
        List<SuccessDTO> successes = new ArrayList<>();

        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()){
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setErrors(true);
            errorDTO.setMessage("User with id: " + userId + " not found");
            errors.add(errorDTO);
            responseBody.setError(errors);
            return responseBody;
        }
        User existingUser = optionalUser.get();

        Optional<Card> optionalCard = cardRepository.findById(cardId);
        if (optionalCard.isEmpty()) {
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setErrors(true);
            errorDTO.setMessage("Card with id: " + cardId + " not found");
            errors.add(errorDTO);
            responseBody.setError(errors);
            return responseBody;
        }

        Card existingCard = optionalCard.get();

        existingUser.getCards().remove(existingCard);

        cardRepository.deleteById(existingCard.getCardId());

        SuccessDTO successDTO = new SuccessDTO();
        successDTO.setSuccess(true);
        successDTO.setMessage("Card was deleted successfully");
        successes.add(successDTO);


        responseBody.setError(errors);
        responseBody.setSuccess(successes);

     return responseBody;
    }
}
