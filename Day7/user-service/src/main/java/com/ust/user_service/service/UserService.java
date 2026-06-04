package com.ust.user_service.service;

import com.ust.user_service.dto.UserRequest;
import com.ust.user_service.dto.UserResponse;
import com.ust.user_service.modal.User;
import com.ust.user_service.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class UserService {
    @Autowired
    UserRepository userRepository;

    public boolean createUser(UserRequest userRequest) {
        try {
            User user = userRepository.save(User.builder()
                    .userName(userRequest.getName())
                    .email(userRequest.getEmail()).build());
            return true;
        } catch (Exception e) {
            log.error("** Failed to create user : {}", userRequest.getEmail());
            throw new RuntimeException(e);
        }
    }

    public UserResponse getUserDetails(Long id) {
        User user = userRepository.getReferenceById(id);
        return UserResponse.builder()
                .name(user.getUserName())
                .email(user.getEmail())
                .build();
    }
}
