package com.assignment.user_service.entity;

import lombok.Data;

//@Entity
//@Table(name = "users")
@Data
public class User {

    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String name;
    private String email;
}
