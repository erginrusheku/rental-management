package com.rental_management.repo;

import com.rental_management.entities.Card;
import com.rental_management.entities.CardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    boolean existsByCardNumber(String cardNumber);

    boolean existsByCardType(CardType cardType);

}
