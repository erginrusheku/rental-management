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

    @GetMapping("/userId")
    ResponseEntity<ResponseBody> getUserById(@RequestParam Long userId){
        ResponseBody userIds = userService.getUserById(userId);
        return new ResponseEntity<>(userIds, HttpStatus.OK);
    }

    @GetMapping("/all")
    ResponseEntity<List<UserDTO>> getAllUsers(){
        List<UserDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/create")
    ResponseEntity<ResponseBody> createUser(@RequestBody UserDTO userDTO){
        ResponseBody createdUser = userService.createUser(userDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/updateUser")
    ResponseEntity<ResponseBody> updateUser(@RequestParam Long userId,@RequestBody UserDTO userDTO){
        ResponseBody updatedUser = userService.updateUser(userId, userDTO);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser")
    ResponseEntity<ResponseBody> deleteUser(@RequestParam Long userId){
       ResponseBody deleteUser =  userService.deleteUser(userId);
        return new ResponseEntity<>(deleteUser, HttpStatus.OK);
    }

    @GetMapping("/{userId}/{cardId}")
    ResponseEntity<User> getCardsByUserId(@PathVariable Long userId, @PathVariable Long cardId){
        User findCards = userService.getCardsByUserId(userId, cardId);
        return new ResponseEntity<>(findCards, HttpStatus.OK);
    }

    @GetMapping("/getReview/{userId}/{reviewId}")
    ResponseEntity<User> getReviewByUserId(@PathVariable Long userId, @PathVariable Long reviewId){
        User findCards = userService.getReviewByUserId(userId, reviewId);
        return new ResponseEntity<>(findCards, HttpStatus.OK);
    }
}
