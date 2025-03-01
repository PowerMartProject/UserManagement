package com.powermart.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.powermart.builder.UserBuilder;
import com.powermart.builder.UserDetailsDtoBuilder;
import com.powermart.dao.UserRepository;
import com.powermart.dto.OtpDto;
import com.powermart.dto.UserDetailsDto;
import com.powermart.dto.UserLoginDto;
import com.powermart.dto.UserSignUpDto;
import com.powermart.exceptions.BuilderException;
import com.powermart.exceptions.EmailNotFoundException;
import com.powermart.exceptions.NullValueException;
import com.powermart.exceptions.OtpGenerationException;
import com.powermart.exceptions.PasswordMismatchException;
import com.powermart.exceptions.UserAlreadyExistsException;
import com.powermart.model.User;
import com.powermart.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserBuilder userBuilder;
	
	@Autowired
	UserDetailsDtoBuilder userDetailsDtoBuilder;
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	

	@Override
	public ResponseEntity<String> signUp(UserSignUpDto userSignUpDto) {
		
		Optional<User> user = userRepository.findUserByEmailId(userSignUpDto.getEmailId());
		User user2 =null;
		if(user.isPresent()) {
			
			logger.error("User already exists with user email : " + userSignUpDto.getEmailId());
			throw new UserAlreadyExistsException("User already exists with email id : " + userSignUpDto.getEmailId());
		}
		
		try {
			user2 = userBuilder.buildUserFromSignUpDto(userSignUpDto);
			logger.info("User Object Built from User Signup Dto");
		}catch(Exception ex) {
			logger.error("Error While converting dto to entity");
			throw new BuilderException("Error While converting dto to entity");
		}
		
		try {
			User savedUser = userRepository.save(user2);
			logger.info("User Saved successfully with user email : "+ savedUser.getEmailId());
			return new ResponseEntity<String>(savedUser.getEmailId(),HttpStatus.CREATED);
			
		}catch(Exception exception) {
			
			logger.error("Failed to Save in DB");
			return new ResponseEntity<String>("Failed to Save in DB",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@Override
	public ResponseEntity<UserDetailsDto> login(UserLoginDto userLoginDto) {
		Optional<User> user = Optional.empty();
		try {
			
			user = userRepository.findUserByEmailId(userLoginDto.getEmailId());
		}catch(Exception ex) {
			logger.error("Failed to Get Details From DB");
			return new ResponseEntity<UserDetailsDto>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		if(user.isPresent()) {
			
			if(user.get().getEmailId().equals(userLoginDto.getEmailId()) && user.get().getPassword().equals(userLoginDto.getPassword())) {
				
				logger.info("Login success for user with user email : "+ userLoginDto.getEmailId());
				try {
					UserDetailsDto userDetailsDto = userDetailsDtoBuilder.buildUserDetailsDtoFromUser(user.get());
					logger.info("User Details Dto Object Built from User Entity");
					return new ResponseEntity<UserDetailsDto>(userDetailsDto,HttpStatus.OK);
				}catch(Exception ex) {
					logger.error("Error While converting Entity to Dto");
					throw new BuilderException("Error While converting Entity to Dto");
				}
			
			}else {
				
				logger.info("Passoword did not match for user with email : "+ userLoginDto.getEmailId());
				throw new PasswordMismatchException("Password did not match for user : "+ userLoginDto.getEmailId());
			}
		}else {
			
			logger.info("No User Found with email id : "+ userLoginDto.getEmailId());
			throw new EmailNotFoundException("No User Found With id : "+userLoginDto.getEmailId());
		}
	}


	public ResponseEntity<UserDetailsDto> getUserDetails(String emailId) {
		
		Optional<User> user = Optional.empty();
		try {
			
			user = userRepository.findUserByEmailId(emailId);
		}catch(Exception ex) {
			logger.error("Failed to Get Details From DB");
			return new ResponseEntity<UserDetailsDto>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(user.isPresent()) {
			try {
				UserDetailsDto userDetailsDto = userDetailsDtoBuilder.buildUserDetailsDtoFromUser(user.get());
				logger.info("User Details Dto Object Built from User Entity");
				return new ResponseEntity<UserDetailsDto>(userDetailsDto,HttpStatus.OK);
			}catch(Exception ex) {
				logger.error("Error While converting Entity to Dto");
				throw new BuilderException("Error While converting Entity to Dto");
			}
		}else {
			logger.info("User Not Found With Email Id : " + emailId);
			throw new EmailNotFoundException("User Not Found With Email Id : " +emailId);
		
		}
	}


	@Override
	public ResponseEntity<String> verifyEmail(String emailId) {
		Optional<User> user = Optional.empty();
		try {
			
			user = userRepository.findUserByEmailId(emailId);
		}catch(Exception ex) {
			logger.error("Failed to Get Details From DB");
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(user.isEmpty()) {
			logger.info("User Not Found With Email Id : " + emailId);
			throw new EmailNotFoundException("User Not Found With Email Id : " +emailId);
		}
		
		String otp = generateOtp(user.get());
		System.out.println(otp);
		return new ResponseEntity<String>("Otp Sent",HttpStatus.OK);
	}
	
	public String generateOtp(User user) {
		String otp = null;
		try {
			otp = String.format("%06d", new Random().nextInt(999999));
			logger.info("Otp generated for user : " + user.getEmailId() + " , Otp : " + otp);
			user.setOtp(otp);
			user.setOtpExpiration(LocalDateTime.now().plusMinutes(5));
			userRepository.save(user);
			logger.info("Otp Saved in db for user : " + user.getEmailId() + " , Otp : " + otp);
		}catch(Exception e) {
			logger.error("Error while generating otp for user : "+ user.getEmailId());
			throw new OtpGenerationException("Otp Geneation Failed");
		}
		return otp;
	}

	@Override
	public ResponseEntity<String> validateOtp(OtpDto otpDto) {
		Optional<User> user = Optional.empty();
		try {
			
			user = userRepository.findUserByEmailId(otpDto.getEmailId());
		}catch(Exception ex) {
			logger.error("Failed to Get Details From DB");
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(user.isPresent()) {
			if(user.get().getOtp().equals(otpDto.getOtp()) && user.get().getOtpExpiration().isAfter(LocalDateTime.now())) {
				return new ResponseEntity<String>("Valid Otp",HttpStatus.OK);
			}else {
				return new ResponseEntity<String>("Invalid Otp",HttpStatus.UNAUTHORIZED); 
			}
		}else {
			logger.info("User Not Found With Email Id : " + otpDto.getEmailId());
			throw new EmailNotFoundException("User Not Found With Email Id : " +otpDto.getEmailId());
		}
	}


	public ResponseEntity<String> changePassword(UserLoginDto userLoginDto) {
		if(ObjectUtils.isEmpty(userLoginDto)) {
			logger.error("Received Null values");
			throw new NullValueException("Received null Values");
		}
		try {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String encodedPassword = passwordEncoder.encode(userLoginDto.getPassword());
			
			
			Optional<User> user = Optional.empty();
			try {
				user = userRepository.findUserByEmailId(userLoginDto.getEmailId());
			}catch(Exception ex) {
				logger.error("Failed to Get Details From DB");
				return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			if(user.isEmpty()) {
				logger.info("User Not Found With Email Id : " + userLoginDto.getEmailId());
				throw new EmailNotFoundException("User Not Found With Email Id : " +userLoginDto.getEmailId());
			}
			
			user.get().setPassword(encodedPassword);
			User savedUser = userRepository.save(user.get());
			if(savedUser !=null) {
				logger.info("Password Updated for user : "+userLoginDto.getEmailId());
				return new ResponseEntity<String>(HttpStatus.OK);
			}else {
				logger.error("Failed to save updated password for user : "+userLoginDto.getEmailId());
				return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		catch(Exception e) {
			logger.error("Failed to save updated password for user : "+userLoginDto.getEmailId());
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
}
