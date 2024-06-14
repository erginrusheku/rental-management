package com.rental_management.service;

import com.rental_management.dto.CardDTO;

import java.util.List;

public interface CardService {
    CardDTO getCardById(Long cardId);
    List<CardDTO> getAllCards();
    CardDTO createCard(CardDTO cardDTO);
    CardDTO updateCard(Long cardId, CardDTO cardDTO);
    void deleteCardById(Long id);


}
