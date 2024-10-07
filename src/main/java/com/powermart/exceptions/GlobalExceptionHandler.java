package com.powermart.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(EmailNotFoundException.class)
	public ResponseEntity<String> emailNotFoundException(EmailNotFoundException exception){
		return new ResponseEntity<String>(exception.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(PasswordMismatchException.class)
	public ResponseEntity<String> passwordMismatchException(PasswordMismatchException exception){
		return new ResponseEntity<String>(exception.getMessage(),HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<String> userAlreadyExistsException(UserAlreadyExistsException exception){
		return new ResponseEntity<String>(exception.getMessage(),HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(OtpGenerationException.class)
	public ResponseEntity<String> otpGenerationFailedException(OtpGenerationException exception){
		return new ResponseEntity<String>(exception.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(NullValueException.class)
	public ResponseEntity<String> nullValueException(NullValueException exception){
		return new ResponseEntity<String>(exception.getMessage(),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(BuilderException.class)
	public ResponseEntity<String> builderException(BuilderException exception){
		return new ResponseEntity<String>(exception.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
