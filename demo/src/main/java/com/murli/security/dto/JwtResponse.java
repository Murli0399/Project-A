package com.murli.security.dto;

import com.murli.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {
	
	private String jwtToken;

	private User user;

}
