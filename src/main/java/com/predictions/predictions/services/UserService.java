package com.predictions.predictions.services;

import com.predictions.predictions.repositories.UserRepository;
import com.predictions.predictions.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService (UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    public User createUser (User user) {

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

       return userRepository.save(user);
    }

    public List<User> getUsers () {

       return userRepository.findAll();
    }

    public void findUser (User user) {

        User foundUSer = null;

        Optional<User> potentialFoundUser = userRepository.findById(user.getId());

      if (potentialFoundUser.isPresent()) {

          foundUSer = potentialFoundUser.get();
      }
      else {
          throw new RuntimeException("no such user");
      }

      if (foundUSer.getPassword().equals(user.getPassword())) {

          System.out.println(user.getEmail() + "should be logged in");

      }

    }
}
