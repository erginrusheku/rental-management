package com.rental_management.service;

import com.rental_management.dto.ResponseBody;
import com.rental_management.dto.ReviewDTO;

import java.util.List;

public interface ReviewService {
    List<ReviewDTO> getAllReview();
    ReviewDTO createReview(ReviewDTO reviewDTO);
    ReviewDTO getById(Long reviewId);
    ReviewDTO updateReview(Long messageId,ReviewDTO reviewDTO);
    void deleteReview(Long reviewId);
    ResponseBody createReviewByUserForProperty(Long userId, Long propertyId, List<ReviewDTO> reviewList);
}
