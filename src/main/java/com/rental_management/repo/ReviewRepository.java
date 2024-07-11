package com.rental_management.repo;

import com.rental_management.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ReviewRepository extends JpaRepository<Review, Long> {

    void deleteReviewByUserId(Long id);

}
