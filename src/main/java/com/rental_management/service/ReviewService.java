package com.rental_management.service;

import com.rental_management.dto.ResponseBody;
import com.rental_management.dto.ReviewDTO;

import java.util.List;

public interface ReviewService {

    List<ReviewDTO> getAllReview();
    ReviewDTO getById(Long reviewId);
    ResponseBody createReviewByUserForProperty(Long userId, Long propertyId, List<ReviewDTO> reviewList);
    ResponseBody updateReviewByUserForProperty(Long userId, Long propertyId, Long reviewId, List<ReviewDTO> reviewList);
    ResponseBody deleteReview(Long reviewId);

}
