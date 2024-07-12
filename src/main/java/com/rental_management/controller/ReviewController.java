package com.rental_management.controller;

import com.rental_management.dto.ResponseBody;
import com.rental_management.dto.ReviewDTO;
import com.rental_management.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/allReviews")
    ResponseEntity<List<ReviewDTO>> getAllReviews() {
        List<ReviewDTO> reviewList = reviewService.getAllReview();
        return new ResponseEntity<>(reviewList, HttpStatus.OK);
    }

    @GetMapping("/getReview/{reviewId}")
    ResponseEntity<ReviewDTO> getById(@PathVariable Long reviewId) {
        ReviewDTO reviewIds = reviewService.getById(reviewId);
        return new ResponseEntity<>(reviewIds, HttpStatus.OK);
    }

    @PutMapping("/updateReview/{userId}/{propertyId}/{reviewId}")
    ResponseEntity<ResponseBody> updateReview(@PathVariable Long userId, @PathVariable Long propertyId, @PathVariable Long reviewId, @RequestBody List<ReviewDTO> reviewList) {
        ResponseBody reviewAndId = reviewService.updateReviewByUserForProperty(userId, propertyId, reviewId, reviewList);
        return new ResponseEntity<>(reviewAndId, HttpStatus.OK);
    }

    @PostMapping("/createReview/{userId}/{propertyId}")
    ResponseEntity<ResponseBody> createReviewByUserForProperty(@PathVariable Long userId, @PathVariable Long propertyId, @RequestBody List<ReviewDTO> reviewList) {
        ResponseBody createReview = reviewService.createReviewByUserForProperty(userId, propertyId, reviewList);
        return new ResponseEntity<>(createReview, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteReview/{reviewId}")
    ResponseEntity<ResponseBody> deleteReview(@PathVariable Long reviewId) {
        ResponseBody deleteReview = reviewService.deleteReview(reviewId);
        return new ResponseEntity<>(deleteReview, HttpStatus.OK);
    }
}
