package com.powermart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.powermart.dto.OtpDto;
import com.powermart.dto.UserDetailsDto;
import com.powermart.dto.UserLoginDto;
import com.powermart.dto.UserSignUpDto;
import com.powermart.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserServiceImpl userService;
	
	@PostMapping("/signup")
	public ResponseEntity<String> signUp(@RequestBody UserSignUpDto userSignUpDto){
		
		return userService.signUp(userSignUpDto);
		
	}
	
	@PostMapping("/login")
	public ResponseEntity<UserDetailsDto> login(@RequestBody UserLoginDto userLoginDto){
		
		return userService.login(userLoginDto);
		
	}
	
	@PostMapping("/forgotpassword")
	public ResponseEntity<String> forgotPassword(@RequestParam(name ="email") String emailId){
		
		return userService.verifyEmail(emailId);
		
	}
	
	@PostMapping("/validate")
	public ResponseEntity<String> validateOtp(@RequestBody OtpDto otpDto){
		return userService.validateOtp(otpDto);
	}
	
	@PostMapping("/resetpassword")
	public ResponseEntity<String> resetPassword(@RequestBody UserLoginDto userLoginDto){
		return userService.changePassword(userLoginDto);
	}
	
	@GetMapping("/{email}")
	public UserDetailsDto getUserDetails(@PathVariable(name="email") String email){
		return userService.getUserDetails(email);
	}

}
