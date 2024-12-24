package com.nikhil.learn.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponseMessage> handleGlobalException(ResourceNotFoundException ex) {
		
		ApiResponseMessage responseMessage = ApiResponseMessage.builder().message(ex.getMessage()).success(true).status(HttpStatus.NOT_FOUND).build();
		
		return new ResponseEntity<>(responseMessage, HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(BadApiRequest.class)
	public ResponseEntity<ApiResponseMessage> handleBadApiRequest(BadApiRequest ex){
		
		ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder().message(ex.getMessage()).success(false).status(HttpStatus.BAD_REQUEST).build();
		
		return new ResponseEntity<>(apiResponseMessage, HttpStatus.BAD_REQUEST);
		
	}

}
