package com.luna.api_car_system.services;

import com.luna.api_car_system.entities.UserEntity;
import com.luna.api_car_system.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void create(UserEntity userEntity) {
        this.userRepository.save(userEntity);
    }

    public List<UserEntity> list(){
        return this.userRepository.findAll();
    }
}
