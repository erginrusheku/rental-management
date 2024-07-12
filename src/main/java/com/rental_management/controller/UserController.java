package com.rental_management.controller;

import com.rental_management.dto.ResponseBody;
import com.rental_management.dto.UserDTO;
import com.rental_management.entities.User;
import com.rental_management.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getUser/{userId}")
    ResponseEntity<ResponseBody> getUserById(@PathVariable Long userId) {
        ResponseBody userIds = userService.getUserById(userId);
        return new ResponseEntity<>(userIds, HttpStatus.OK);
    }

    @GetMapping("/allUsers")
    ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/createUser")
    ResponseEntity<ResponseBody> createUser(@RequestBody UserDTO userDTO) {
        ResponseBody createdUser = userService.createUser(userDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/updateUser/{userId}")
    ResponseEntity<ResponseBody> updateUser(@PathVariable Long userId, @RequestBody UserDTO userDTO) {
        ResponseBody updatedUser = userService.updateUser(userId, userDTO);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser/{userId}")
    ResponseEntity<ResponseBody> deleteUser(@PathVariable Long userId) {
        ResponseBody deleteUser = userService.deleteUser(userId);
        return new ResponseEntity<>(deleteUser, HttpStatus.OK);
    }

    @GetMapping("/{userId}/{cardId}")
    ResponseEntity<User> getCardsByUserId(@PathVariable Long userId, @PathVariable Long cardId) {
        User findCards = userService.getCardsByUserId(userId, cardId);
        return new ResponseEntity<>(findCards, HttpStatus.OK);
    }

    @GetMapping("/getReview/{userId}/{reviewId}")
    ResponseEntity<User> getReviewByUserId(@PathVariable Long userId, @PathVariable Long reviewId) {
        User findCards = userService.getReviewByUserId(userId, reviewId);
        return new ResponseEntity<>(findCards, HttpStatus.OK);
    }
}
