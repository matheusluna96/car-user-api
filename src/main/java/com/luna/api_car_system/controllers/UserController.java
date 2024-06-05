package com.luna.api_car_system.controllers;

import com.luna.api_car_system.entities.UserEntity;
import com.luna.api_car_system.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public void create(@RequestBody UserEntity userEntity){
        this.userService.create(userEntity);
    }

    @GetMapping
    public ResponseEntity<List<UserEntity>> list(){
        List<UserEntity> userEntities = this.userService.list();
        return ResponseEntity.ok(userEntities);
    }
}
