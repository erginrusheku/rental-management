package com.rental_management.controller;

import com.rental_management.dto.CardDTO;
import com.rental_management.dto.ResponseBody;
import com.rental_management.service.CardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/card/{cardId}")
    ResponseEntity<CardDTO> getCardById(@PathVariable Long cardId) {
        CardDTO cardIds = cardService.getCardById(cardId);
        return new ResponseEntity<>(cardIds, HttpStatus.OK);
    }

    @GetMapping("/allCards")
    ResponseEntity<List<CardDTO>> getAllCards() {
        List<CardDTO> cards = cardService.getAllCards();
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }

    @PostMapping("/createCard/{userId}")
    public ResponseEntity<ResponseBody> createCardByUser(@PathVariable Long userId, @RequestBody List<CardDTO> cardDTOList) {
        ResponseBody createCard = cardService.createCardByUser(userId, cardDTOList);
        return new ResponseEntity<>(createCard, HttpStatus.CREATED);
    }

    @PutMapping("/updateCard/{userId}/{cardId}")
    public ResponseEntity<ResponseBody> updateCardByUser(@PathVariable Long userId, @PathVariable Long cardId, @RequestBody List<CardDTO> cardList) {
        ResponseBody updateCard = cardService.updateCardByUser(userId, cardId, cardList);
        return new ResponseEntity<>(updateCard, HttpStatus.OK);
    }

    @DeleteMapping("/deleteCard/{userId}/{cardId}")
    public ResponseEntity<ResponseBody> deleteCardByUser(@PathVariable Long userId, @PathVariable Long cardId) {
        ResponseBody deleteCard = cardService.deleteCardByUser(userId, cardId);
        return new ResponseEntity<>(deleteCard, HttpStatus.OK);
    }
}

