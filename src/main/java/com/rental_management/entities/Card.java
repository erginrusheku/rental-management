package com.rental_management.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "card", uniqueConstraints = {
        @UniqueConstraint(columnNames = "cardNumber")
})
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cardId;

    private String cardholderName;

    @Column(unique = true, length = 16)
    @Pattern(regexp = "\\d{16}", message = "Card Number must be exactly 16 digits")
    private String cardNumber;

    @Min(value = 100, message = "CVV must be at least 100")
    @Max(value = 999, message = "CVV must be at most 999")
    private int cvv;

    @Enumerated(EnumType.STRING)
    private CardType cardType;

    private Date creationDate;

    private Date expirationDate;

    @JsonIgnore
    @ManyToOne
    private User user;
}
