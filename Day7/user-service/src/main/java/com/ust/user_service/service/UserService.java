package com.ust.user_service.service;

import com.ust.user_service.dto.UserRequest;
import com.ust.user_service.dto.UserResponse;
import com.ust.user_service.exception.customExceptions.UserNotFoundException;
import com.ust.user_service.modal.User;
import com.ust.user_service.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
public class UserService {
    @Autowired
    UserRepository userRepository;

    public boolean createUser(UserRequest userRequest) {
        if(userRepository.existsByUserEmail(userRequest.getEmail())) {
            throw new RuntimeException("User already exits!. Please try to add another user.");
        }

        try {
            User user = userRepository.save(User.builder()
                    .userName(userRequest.getName())
                    .userEmail(userRequest.getEmail()).build());
            log.info("User is created successfully : {}", user.toString());
            return true;
        } catch (DataIntegrityViolationException e) {
            log.error("** User with email already exists : {}", userRequest.getEmail());
            throw new RuntimeException("User with email '" + userRequest.getEmail() + "' already exists!");
        } catch (Exception e) {
            log.error("** Failed to create user : {}", userRequest.getEmail());
            throw new RuntimeException(e);
        }
    }

    public UserResponse getUserDetails(Long id) {
        User user = userRepository.getUserByUserId(id);
        if(user != null) {
            return UserResponse.builder()
                    .name(user.getUserName())
                    .email(user.getUserEmail())
                    .build();
        } else {
            log.error("User not found");
            throw new UserNotFoundException("User not found!");
        }
    }
}
