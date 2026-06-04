package com.ust.user_service.controller;

import com.ust.user_service.dto.ApiResponse;
import com.ust.user_service.dto.UserRequest;
import com.ust.user_service.dto.UserResponse;
import com.ust.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public ApiResponse<UserResponse> createUser(@RequestBody UserRequest userRequest) {
        boolean isUserCreated = userService.createUser(userRequest);
        if(isUserCreated) {
            return new ApiResponse<UserResponse>(true, "User Created Successfully!", null);
        } else {
            return new ApiResponse<UserResponse>(false, "Failed to create user!", null);
        }
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUserDetails(@PathVariable("id") Long id) {
        UserResponse userResponse = userService.getUserDetails(id);

        if(userResponse != null) {
            return new ApiResponse<UserResponse>(true, "User details fetched successfully!", userResponse);
        } else {
            return new ApiResponse<UserResponse>(false, "Requested user details not found!", null);
        }
    }
}
