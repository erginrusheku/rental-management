package com.rental_management.repo;

import com.rental_management.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface MessageRepository extends JpaRepository<Message, Long> {

  void deleteMessageByOwnerId(Long messageOwnerId);

  void deleteMessageByUserId(Long messageUserId);

}


