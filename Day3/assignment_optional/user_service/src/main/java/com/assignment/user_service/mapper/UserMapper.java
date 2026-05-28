package com.assignment.user_service.mapper;

import com.assignment.user_service.dto.UserResponse;
import com.assignment.user_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mappings({
            @Mapping(source = "id", target = "userId"),
            @Mapping(source = "name", target = "userName"),
            @Mapping(source = "email", target = "userEmail")
    })
    UserResponse toDTO(User user);

    @Mappings({
            @Mapping(source = "userId", target = "id"),
            @Mapping(source = "userName", target = "name"),
            @Mapping(source = "userEmail", target = "email")
    })
    User toEntity(UserResponse userResponse);}
