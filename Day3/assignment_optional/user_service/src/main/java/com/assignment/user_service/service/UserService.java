package com.assignment.user_service.service;

import com.assignment.user_service.dto.UserResponse;
import com.assignment.user_service.entity.User;
import com.assignment.user_service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public UserResponse getUserById(String userId) {
        // In a real application, you would fetch user details from a database as a User Entity.
        // Here, we are returning a dummy user for demonstration purposes.
        User user = new User();
        user.setId(userId);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");

        //Convert using mapper
        return userMapper.toDTO(user);
    }
}
