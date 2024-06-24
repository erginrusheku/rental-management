package com.rental_management.service;

import com.rental_management.dto.*;
import com.rental_management.entities.Card;
import com.rental_management.entities.Owner;
import com.rental_management.entities.User;
import com.rental_management.repo.CardRepository;
import com.rental_management.repo.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
    public CardDTO createCard(CardDTO cardDTO) {
        return null;
    }

    @Override
    public CardDTO updateCard(Long cardId, CardDTO cardDTO) {
        return null;
    }

    @Override
    public void deleteCardById(Long id) {

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
                    if (cardDTO.getCardNumber() == null || cardDTO.getCardType() == null) {
                        ErrorDTO error = new ErrorDTO();
                        error.setErrors(true);
                        error.setMessage("Card without Number, or Type wasn't created!");
                        errors.add(error);
                        return null;
                    } else {
                        Card card = modelMapper.map(cardDTO, Card.class);
                        Card createdCard = cardRepository.save(card);
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

        user.setCards(createdCards);
        User savedUser = userRepository.save(user);

        createdCards.forEach(card -> card.setUser(savedUser));
        cardRepository.saveAll(createdCards);

        responseBody.setError(errors);
        responseBody.setSuccess(successes);

        modelMapper.map(user, UserDTO.class);

        return responseBody;
    }
}
