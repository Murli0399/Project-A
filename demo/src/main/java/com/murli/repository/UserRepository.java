package com.murli.repository;

import com.murli.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User,Integer> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

}
