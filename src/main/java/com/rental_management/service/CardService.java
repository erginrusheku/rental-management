package com.rental_management.service;

import com.rental_management.dto.CardDTO;
import com.rental_management.dto.ResponseBody;

import java.util.List;

public interface CardService {
    CardDTO getCardById(Long cardId);
    List<CardDTO> getAllCards();
    CardDTO createCard(CardDTO cardDTO);
    CardDTO updateCard(Long cardId, CardDTO cardDTO);
    void deleteCardById(Long id);
    ResponseBody createCardByUser(Long userId, List<CardDTO> cardDTO);
    ResponseBody updateCardByUser(Long userId, Long cardId, List<CardDTO> cardList);


}
