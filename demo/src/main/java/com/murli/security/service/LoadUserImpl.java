package com.murli.security.service;

import java.util.ArrayList;
import java.util.List;

import com.murli.model.User;
import com.murli.repository.UserRepository;
import com.murli.security.dto.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class LoadUserImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private UserInfo userInfo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepo.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with Username " + username));

		System.out.println("user found " + user);

		userInfo.setUser(user);

		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(user.getRole()));

		return new org.springframework.security.core.userdetails.User(username, user.getPassword(), authorities);
	}

}
