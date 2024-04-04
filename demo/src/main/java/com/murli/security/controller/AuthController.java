package com.murli.security.controller;

import com.murli.security.dto.JwtResponse;
import com.murli.security.dto.LoginRequest;
import com.murli.security.service.JwtHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserDetailsService userDetailsService;

	private AuthenticationManager manager;

	@Autowired
	private JwtHelper helper;

	private Logger logger = LoggerFactory.getLogger(AuthController.class);

	@GetMapping("/login")
	public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request) {
		
		logger.info("Inside Login"+request);
		

		if (manager != null)
			this.doAuthenticate(request.getUsername(), request.getPassword());

		UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
		String token = this.helper.generateToken(userDetails);

		com.murli.model.User user = new com.murli.model.User();

		user.setUsername(userDetails.getUsername());
		user.setRole(userDetails.getAuthorities().toString());

		JwtResponse response = new JwtResponse(token,user);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	private void doAuthenticate(String email, String password) throws BadCredentialsException {

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
		try {
			manager.authenticate(authentication);

		} catch (BadCredentialsException e) {
			throw new BadCredentialsException(" Invalid Username or Password  !!");
		}

	}

}