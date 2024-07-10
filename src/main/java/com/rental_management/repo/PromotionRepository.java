package com.rental_management.repo;

import com.rental_management.entities.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    void deletePromotionByOwnerId(Long id);
}
