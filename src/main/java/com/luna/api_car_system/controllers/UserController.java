package com.luna.api_car_system.controllers;

import com.luna.api_car_system.entities.UserEntity;
import com.luna.api_car_system.exceptions.EmailAlreadyExistsException;
import com.luna.api_car_system.exceptions.LoginAlreadyExistsException;
import com.luna.api_car_system.exceptions.UserAlreadyExistsException;
import com.luna.api_car_system.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserEntity> create(@Valid @RequestBody UserEntity user){
        try {
            UserEntity createdUser = userService.create(user);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (EmailAlreadyExistsException | LoginAlreadyExistsException e) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }

    }

    @GetMapping
    public ResponseEntity<List<UserEntity>> list(){
        return ResponseEntity.ok(this.userService.list());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            if (error.getDefaultMessage().contains("mandatory")) {
                errors.put(error.getField(), "Missing fields");
            } else {
                errors.put(error.getField(), "Invalid fields");
            }
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<String> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        return new ResponseEntity<>("Email already exists", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(LoginAlreadyExistsException.class)
    public ResponseEntity<String> handleLoginAlreadyExistsException(LoginAlreadyExistsException ex) {
        return new ResponseEntity<>("Login already exists", HttpStatus.CONFLICT);
    }
}
