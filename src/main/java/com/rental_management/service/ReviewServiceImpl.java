package com.rental_management.service;

import com.rental_management.dto.*;
import com.rental_management.entities.Property;
import com.rental_management.entities.Review;
import com.rental_management.entities.User;
import com.rental_management.repo.PropertyRepository;
import com.rental_management.repo.ReviewRepository;
import com.rental_management.repo.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService{
    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;
    private final ModelMapper modelMapper;
    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(UserRepository userRepository, PropertyRepository propertyRepository, ModelMapper modelMapper, ReviewRepository reviewRepository) {
        this.userRepository = userRepository;
        this.propertyRepository = propertyRepository;
        this.modelMapper = modelMapper;
        this.reviewRepository = reviewRepository;
    }

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
    @Override
    public ResponseBody createReviewByUserForProperty(Long userId, Long propertyId, List<ReviewDTO> reviewList){
        ResponseBody responseBody = new ResponseBody();
        List<ErrorDTO> errorList = new ArrayList<>();
        List<SuccessDTO> successList = new ArrayList<>();

        Optional<User> existingUser =  userRepository.findById(userId);
        if(existingUser.isEmpty()){
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setErrors(true);
            errorDTO.setMessage("User not found");
            errorList.add(errorDTO);
            responseBody.setError(errorList);
            return responseBody;
        }

        User optionalUser = existingUser.get();

        Optional<Property> existingProperty = propertyRepository.findById(propertyId);
        if(existingProperty.isEmpty()){
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setErrors(true);
            errorDTO.setMessage("Property not found");
            errorList.add(errorDTO);
            responseBody.setError(errorList);
            return responseBody;
        }

        Property optionalProperty = existingProperty.get();

        List<Review> reviews = reviewList.stream().map(reviewDTO -> {
            Review review = modelMapper.map(reviewDTO, Review.class);
            Review createReview = reviewRepository.save(review);

            SuccessDTO successDTO = new SuccessDTO();
            successDTO.setSuccess(true);
            successDTO.setMessage("Review was created successfully");
            successList.add(successDTO);
            responseBody.setSuccess(successList);
            return createReview;
        }).filter(Objects::nonNull).collect(Collectors.toList());

        optionalUser.setReviews(reviews);
        User savedUser = userRepository.save(optionalUser);

        optionalProperty.setReviews(reviews);
        Property savedProperty = propertyRepository.save(optionalProperty);

        reviews.forEach(review -> review.setUser(savedUser));
        reviews.forEach(review -> review.setProperty(savedProperty));
        reviewRepository.saveAll(reviews);

        modelMapper.map(savedUser, UserDTO.class);
        modelMapper.map(savedProperty, PropertyDTO.class);


        return responseBody;
    }
}
