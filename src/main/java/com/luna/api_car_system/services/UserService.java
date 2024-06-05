package com.luna.api_car_system.services;

import com.luna.api_car_system.entities.UserEntity;
import com.luna.api_car_system.exceptions.EmailAlreadyExistsException;
import com.luna.api_car_system.exceptions.LoginAlreadyExistsException;
import com.luna.api_car_system.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity create(UserEntity user) {
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("PUBLIC.USER_ENTITY(EMAIL NULLS FIRST)")) {
                throw new EmailAlreadyExistsException("Email already exists");
            } else if (e.getMessage().contains("PUBLIC.USER_ENTITY(LOGIN NULLS FIRST)")) {
                throw new LoginAlreadyExistsException("Login already exists");
            } else {
                System.out.println("Message"+e.getMessage());
                throw e;
            }
        }

    }

    public List<UserEntity> list(){
        return this.userRepository.findAll();
    }

    public Optional<UserEntity> getUser(Long id){
        return this.userRepository.findById(id);
    }

    public void deleteUser(Long id){
        this.userRepository.deleteById(id);
    }
}
