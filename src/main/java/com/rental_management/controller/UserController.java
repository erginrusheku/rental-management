package com.rental_management.controller;

import com.rental_management.dto.ResponseBody;
import com.rental_management.dto.UserDTO;
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

    @GetMapping("/userId/{userId}")
    ResponseEntity<UserDTO> getUserById(@PathVariable Long userId){
        UserDTO userIds = userService.getUserById(userId);
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

    @PutMapping("/updateUser/{userId}")
    ResponseEntity<UserDTO> updateUser(@PathVariable Long userId,@RequestBody UserDTO userDTO){
        UserDTO updatedUser = userService.updateUser(userId, userDTO);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/deleteUserId/{userId}")
    ResponseEntity<Void> deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
