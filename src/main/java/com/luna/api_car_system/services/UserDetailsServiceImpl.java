package com.luna.api_car_system.services;

import com.luna.api_car_system.entities.UserEntity;
import com.luna.api_car_system.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = this.userRepository.findByLogin(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found with login: " + username)
        );
        return new User(user.getLogin(), user.getPassword(), new ArrayList<>());
    }
}
