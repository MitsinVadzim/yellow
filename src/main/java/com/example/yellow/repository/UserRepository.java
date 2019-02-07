package com.example.yellow.repository;

import com.example.yellow.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
    User findByUsername(String username);

    User findByActivationCode(String code);

    User findById(Long id);
}
