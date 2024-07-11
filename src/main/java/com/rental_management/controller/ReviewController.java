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

    @GetMapping("/all")
    ResponseEntity<List<ReviewDTO>> getAllReviews() {
        List<ReviewDTO> reviewList = reviewService.getAllReview();
        return new ResponseEntity<>(reviewList, HttpStatus.OK);
    }

    @GetMapping("/reviewId")
    ResponseEntity<ReviewDTO> getById(@RequestParam Long reviewId) {
        ReviewDTO reviewIds = reviewService.getById(reviewId);
        return new ResponseEntity<>(reviewIds, HttpStatus.OK);
    }

    @PutMapping("/updateReview")
    ResponseEntity<ResponseBody> updateReview(@RequestParam Long userId, @RequestParam Long propertyId, @RequestParam Long reviewId, @RequestBody List<ReviewDTO> reviewList) {
        ResponseBody reviewAndId = reviewService.updateReviewByUserForProperty(userId, propertyId, reviewId, reviewList);
        return new ResponseEntity<>(reviewAndId, HttpStatus.OK);
    }

    @PostMapping("/createReview")
    ResponseEntity<ResponseBody> createReviewByUserForProperty(@RequestParam Long userId, @RequestParam Long propertyId, @RequestBody List<ReviewDTO> reviewList) {
        ResponseBody createReview = reviewService.createReviewByUserForProperty(userId, propertyId, reviewList);
        return new ResponseEntity<>(createReview, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteReview")
    ResponseEntity<ResponseBody> deleteReview(@RequestParam Long reviewId) {
        ResponseBody deleteReview = reviewService.deleteReview(reviewId);
        return new ResponseEntity<>(deleteReview, HttpStatus.OK);
    }
}
