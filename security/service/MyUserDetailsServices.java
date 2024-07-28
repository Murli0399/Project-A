package com.murli.security.service;

import com.murli.model.User;
import com.murli.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsServices implements UserDetailsService {

    @Autowired
    private UserRepository uDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = uDao.findByUsername(username);
        if (user.isPresent()) {
            return new MyUserDetail(user.get());
        }
        throw new UsernameNotFoundException("(My UserDetails Services ) User not present in this username " + username);

    }
}
