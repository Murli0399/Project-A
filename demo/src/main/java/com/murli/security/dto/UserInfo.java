package com.murli.security.dto;

import com.murli.model.User;
import org.springframework.stereotype.Component;


import lombok.Data;

@Data
@Component
public class UserInfo {
	
	private User user;

}
