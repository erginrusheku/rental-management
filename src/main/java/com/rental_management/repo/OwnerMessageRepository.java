package com.rental_management.repo;

import com.rental_management.entities.OwnerMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerMessageRepository extends JpaRepository<OwnerMessage, Long> {
}
