package com.predictions.predictions.services;

import com.predictions.predictions.repositories.UserRepository;
import com.predictions.predictions.models.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService (UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    public User createUser (User user) {

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
