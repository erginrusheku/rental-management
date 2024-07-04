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

import java.time.Instant;
import java.util.*;
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
    public ReviewDTO getById(Long reviewId) {
        return null;
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

            if(review.getComment() == null){
                ErrorDTO  errorDTO = new ErrorDTO();
                errorDTO.setErrors(true);
                errorDTO.setMessage("You can't create a review without a comment");
                errorList.add(errorDTO);
                responseBody.setError(errorList);
                return null;
            }
            review.setDate(Date.from(Instant.now()));

            Review createReview = reviewRepository.save(review);
            createReview.setUser(optionalUser);
            optionalUser.getReviews().add(createReview);
            createReview.setProperty(optionalProperty);
            optionalProperty.getReviews().add(createReview);


            SuccessDTO successDTO = new SuccessDTO();
            successDTO.setSuccess(true);
            successDTO.setMessage("Review was created successfully");
            successList.add(successDTO);
            responseBody.setSuccess(successList);
            return createReview;
        }).filter(Objects::nonNull).collect(Collectors.toList());


        userRepository.save(optionalUser);

        propertyRepository.save(optionalProperty);

        reviewRepository.saveAll(reviews);

        return responseBody;
    }

    @Override
    public ResponseBody updateReviewByUserForProperty(Long userId, Long propertyId, Long reviewId, List<ReviewDTO> reviewList) {
        ResponseBody responseBody = new ResponseBody();
        List<SuccessDTO> successes = new ArrayList<>();
        List<ErrorDTO> errors = new ArrayList<>();

        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()){
            ErrorDTO error = new ErrorDTO();
            error.setErrors(true);
            error.setMessage("User could not be found");
            errors.add(error);
            responseBody.setError(errors);
            return responseBody;
        }

        User existingUser = optionalUser.get();

        Optional<Property> optionalProperty = propertyRepository.findById(propertyId);
        if(optionalProperty.isEmpty()){
            ErrorDTO error = new ErrorDTO();
            error.setErrors(true);
            error.setMessage("Property could not be found");
            errors.add(error);
            responseBody.setError(errors);
            return responseBody;
        }

        Property existingProperty = optionalProperty.get();

        Optional<Review> optionalReview = reviewRepository.findById(reviewId);
        if(optionalReview.isEmpty()){
            ErrorDTO error = new ErrorDTO();
            error.setErrors(true);
            error.setMessage("Property could not be found");
            errors.add(error);
            responseBody.setError(errors);
            return responseBody;
        }

        Review existingReview = optionalReview.get();

        List<Review> reviews = reviewList.stream().map(reviewDTO -> {
            if(existingReview.getComment() == null){
                ErrorDTO error = new ErrorDTO();
                error.setErrors(true);
                error.setMessage("You can't create a review without a comment");
                errors.add(error);
                responseBody.setError(errors);
                return null;
            }
            existingReview.setDate(Date.from(Instant.now()));
            modelMapper.map(reviewDTO, existingReview);

            Review updatedReview = reviewRepository.save(existingReview);

            updatedReview.setUser(existingUser);
            existingUser.getReviews().add(updatedReview);
            updatedReview.setProperty(existingProperty);
            existingProperty.getReviews().add(updatedReview);

            SuccessDTO success = new SuccessDTO();
            success.setSuccess(true);
            success.setMessage("Review with id: " + reviewId + " was updated successfully");
            successes.add(success);
            responseBody.setSuccess(successes);
            return updatedReview;

        }).filter(Objects::nonNull).collect(Collectors.toList());


         userRepository.save(existingUser);

         propertyRepository.save(existingProperty);

         reviewRepository.saveAll(reviews);

        return responseBody;
    }

    public ResponseBody deleteReview(Long userId, Long propertyId, Long reviewId){
        ResponseBody responseBody = new ResponseBody();
        List<SuccessDTO> successes = new ArrayList<>();
        List<ErrorDTO> errors = new ArrayList<>();

        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()){
            ErrorDTO error = new ErrorDTO();
            error.setErrors(true);
            error.setMessage("User could not be found");
            errors.add(error);
            responseBody.setError(errors);
            return responseBody;
        }

        User existingUser = optionalUser.get();

        Optional<Property> optionalProperty = propertyRepository.findById(propertyId);
        if(optionalProperty.isEmpty()){
            ErrorDTO error = new ErrorDTO();
            error.setErrors(true);
            error.setMessage("Property could not be found");
            errors.add(error);
            responseBody.setError(errors);
            return responseBody;
        }

        Property existingProperty = optionalProperty.get();

        Optional<Review> optionalReview = reviewRepository.findById(reviewId);
        if(optionalReview.isEmpty()){
            ErrorDTO error = new ErrorDTO();
            error.setErrors(true);
            error.setMessage("Property could not be found");
            errors.add(error);
            responseBody.setError(errors);
            return responseBody;
        }

        Review existingReview = optionalReview.get();

        existingUser.getReviews().remove(existingReview);
        existingProperty.getReviews().remove(existingReview);

        reviewRepository.delete(existingReview);

        SuccessDTO successDTO = new SuccessDTO();
        successDTO.setSuccess(true);
        successDTO.setMessage("Review was deleted successfully");
        successes.add(successDTO);
        responseBody.setSuccess(successes);


        responseBody.setError(errors);
        responseBody.setSuccess(successes);

        return responseBody;
    }
}
