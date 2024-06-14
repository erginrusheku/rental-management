package com.rental_management.service;

import com.rental_management.dto.CardDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardServiceImpl implements CardService{
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
}
