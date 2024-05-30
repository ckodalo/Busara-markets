package com.predictions.predictions.repositories;

import com.predictions.predictions.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {


}
