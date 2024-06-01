package com.predictions.predictions.services;

import com.predictions.predictions.models.User;
import com.predictions.predictions.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

       User foundUser = userRepository.findByUsername(username);

       if (foundUser == null) {

           throw new UsernameNotFoundException("username not found");

       }

       UserDetails userDetails =
               org.springframework.security.core.userdetails.User.builder()
                       .username(foundUser.getUsername())
                       .password(foundUser.getPassword())
                       .build();


       return userDetails;

    }
}
