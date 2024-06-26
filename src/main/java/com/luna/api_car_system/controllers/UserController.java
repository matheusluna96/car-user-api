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
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<UserEntity> create(@Valid @RequestBody UserEntity user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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

    @GetMapping("{id}")
    public ResponseEntity<UserEntity> getUser(@PathVariable Long id){
        return ResponseEntity.of(this.userService.getUser(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        this.userService.deleteUser(id);
        return ResponseEntity.ok("Usuário removido com sucesso!");
    }

    @PutMapping("{id}")
    public ResponseEntity<UserEntity> update(@PathVariable Long id, @RequestBody UserEntity user){
        UserEntity userUpdated = this.userService.create(user);
        return ResponseEntity.ok(userUpdated);
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
