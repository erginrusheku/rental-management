package com.rental_management.service;

import com.rental_management.dto.ReviewDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService{
    @Override
    public List<ReviewDTO> getAllReview() {
        return List.of();
    }

    @Override
    public ReviewDTO createReview(ReviewDTO reviewDTO) {
        return null;
    }

    @Override
    public ReviewDTO getById(Long reviewId) {
        return null;
    }

    @Override
    public ReviewDTO updateReview(Long messageId, ReviewDTO reviewDTO) {
        return null;
    }

    @Override
    public void deleteReview(Long reviewId) {

    }
}
