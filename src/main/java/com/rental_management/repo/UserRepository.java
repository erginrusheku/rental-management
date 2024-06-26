package com.rental_management.repo;

import com.rental_management.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM users u WHERE u.id = :userId AND EXISTS (SELECT 1 FROM card c WHERE c.user_id = u.id AND c.card_id = :cardId)", nativeQuery = true)
    User getCardsByUserId(@Param("userId") Long userId, @Param("cardId") Long cardId);
}
