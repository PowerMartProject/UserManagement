package com.powermart.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSignUpDto {
	
	private String emailId;
	
	private String password;
	
	private String firstName;
	
	private String lastName;
	
	private Set<String> roles;
}
