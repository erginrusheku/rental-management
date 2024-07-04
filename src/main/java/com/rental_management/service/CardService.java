package com.rental_management.service;

import com.rental_management.dto.CardDTO;
import com.rental_management.dto.ResponseBody;

import java.util.List;

public interface CardService {
    CardDTO getCardById(Long cardId);
    List<CardDTO> getAllCards();
    ResponseBody createCardByUser(Long userId, List<CardDTO> cardDTO);
    ResponseBody updateCardByUser(Long userId, Long cardId, List<CardDTO> cardList);
    ResponseBody deleteCardByUser(Long userId, Long cardId);



}
