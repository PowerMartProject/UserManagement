package com.powermart.service;

import org.springframework.http.ResponseEntity;

import com.powermart.dto.OtpDto;
import com.powermart.dto.UserDetailsDto;
import com.powermart.dto.UserLoginDto;
import com.powermart.dto.UserSignUpDto;

public interface UserService {
	
	public ResponseEntity<String> signUp(UserSignUpDto userSignUpDto);
	
	public ResponseEntity<UserDetailsDto> login(UserLoginDto userLoginDto);
	
	public ResponseEntity<String> verifyEmail(String emailId);
	
	public ResponseEntity<String> validateOtp(OtpDto otpDto);
}
