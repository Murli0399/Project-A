package com.murli.service.serviceImpl;

import com.murli.model.User;
import com.murli.repository.UserRepository;
import com.murli.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User register(User user) {
        if (user == null)
            throw new RuntimeException("You can not pass the null value for save the new User");

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("User apready present so we can not Store new User with same Id");
        }

        return userRepository.save(user);
    }

}
