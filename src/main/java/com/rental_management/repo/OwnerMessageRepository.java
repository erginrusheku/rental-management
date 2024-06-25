package com.rental_management.repo;

import com.rental_management.entities.OwnerMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerMessageRepository extends JpaRepository<OwnerMessage, Long> {
}
