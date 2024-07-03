package com.rental_management.controller;

import com.rental_management.dto.CardDTO;
import com.rental_management.dto.ResponseBody;
import com.rental_management.service.CardService;
import org.springframework.data.annotation.CreatedBy;
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

    @GetMapping("/card}")
    ResponseEntity<CardDTO> getCardById(@RequestParam Long cardId){
        CardDTO cardIds = cardService.getCardById(cardId);
        return new ResponseEntity<>(cardIds, HttpStatus.OK);
    }

    @GetMapping("/all")
    ResponseEntity<List<CardDTO>> getAllCards(){
        List<CardDTO> cards = cardService.getAllCards();
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }

    @PostMapping("/create")
    ResponseEntity<CardDTO> createCard(@RequestBody CardDTO cardDTO){
        CardDTO createdCard = cardService.createCard(cardDTO);
        return new ResponseEntity<>(createdCard, HttpStatus.CREATED);
    }

    @PutMapping("/updateCard")
    ResponseEntity<CardDTO> updateCard(@RequestParam Long cardId,@RequestBody CardDTO cardDTO){
        CardDTO updatedCard = cardService.updateCard(cardId, cardDTO);
        return new ResponseEntity<>(updatedCard, HttpStatus.OK);
    }

    @DeleteMapping("/deleteCardId")
    ResponseEntity<Void> deleteCard(@RequestParam Long cardId){
        cardService.deleteCardById(cardId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/createCard")
    public ResponseEntity<ResponseBody> createCardByUser(@RequestParam Long userId, @RequestBody List<CardDTO> cardDTOList) {
        ResponseBody createCard = cardService.createCardByUser(userId, cardDTOList);
        return new ResponseEntity<>(createCard, HttpStatus.CREATED);
    }

    @PutMapping("/updateCard")
    public ResponseEntity<ResponseBody> updateCardByUser(@RequestParam Long userId, @RequestParam Long cardId, @RequestBody List<CardDTO> cardList){
        ResponseBody updateCard = cardService.updateCardByUser(userId,cardId,cardList);
        return new ResponseEntity<>(updateCard, HttpStatus.OK);
    }

    @DeleteMapping("/deleteCard")
    public ResponseEntity<ResponseBody> deleteCardByUser(@RequestParam Long userId, @RequestParam Long cardId){
        ResponseBody deleteCard = cardService.deleteCardByUser(userId,cardId);
        return new ResponseEntity<>(deleteCard, HttpStatus.OK);
    }
}

