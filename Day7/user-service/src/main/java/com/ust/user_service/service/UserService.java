package com.ust.user_service.service;

import com.ust.user_service.dto.OrderStatusEvent;
import com.ust.user_service.dto.UserRequest;
import com.ust.user_service.dto.UserResponse;
import com.ust.user_service.exception.customExceptions.UserNotFoundException;
import com.ust.user_service.modal.OrderNotification;
import com.ust.user_service.modal.User;
import com.ust.user_service.repository.OrderNotificationRepository;
import com.ust.user_service.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderNotificationRepository orderNotificationRepository;

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

    public void updateOrderStausNotification(OrderStatusEvent event) {
        log.info("Received order notification event for : {} order", event.getOrderId());

        OrderNotification notification = OrderNotification.builder()
                .userId(event.getUserId())
                .orderId(event.getOrderId())
                .status(event.getStatus())
                .remark(event.getRemark())
                .build();

        try {
            orderNotificationRepository.save(notification);
        } catch (Exception e) {
            log.error("Failed to update order notification : {}", String.valueOf(e));
            throw new RuntimeException(e);
        }
    }
}
