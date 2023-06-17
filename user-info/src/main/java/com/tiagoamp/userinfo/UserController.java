package com.tiagoamp.userinfo;


import com.tiagoamp.userinfo.repository.UserEntity;
import com.tiagoamp.userinfo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {

    private UserRepository userRepository;

    @GetMapping("/user")
    private List<UserEntity> getProducts() {
        return userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    private UserEntity getProduct(@PathVariable Long id) {
        return userRepository.findUserById(id);
    }
}

//http://localhost:8082/api/user/901
//
//        {
//        "id": 901,
//        "name": "Isaac Newton",
//        "email": "isaac@newton.com",
//        "birthdate": "1643-01-04",
//        "address": "gravity street - England"
//        }
