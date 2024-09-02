package com.example.demo.exceptions.handler;

import java.util.List;
import java.util.NoSuchElementException;

import jakarta.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.demo.exceptions.ClientErrorException;
import com.example.demo.exceptions.ErrorResponse;
import com.example.demo.security.jwt.exception.TokenRefreshException;
import com.example.demo.security.jwt.payload.response.TokenRefreshErrorResponse;
import com.example.demo.security.jwt.payload.response.TokenRefreshErrorStatusResponse;

@ControllerAdvice
public class ControllerExceptionManager {

	
	@ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleNoResource() {
    	ErrorResponse errorResponse = new ErrorResponse();
    	errorResponse.setCode("400");
    	errorResponse.setStatus("Request Failed");
    	errorResponse.setReason("Requested Resource is not available");
    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
	
	@ExceptionHandler(ClientErrorException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceWithId(ClientErrorException ex) {
    	ErrorResponse errorResponse = new ErrorResponse();
    	errorResponse.setCode("400");
    	errorResponse.setStatus("Request Failed");
      	errorResponse.setReason(ex.getMessage());
    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    	ErrorResponse errorResponse = new ErrorResponse();
    	errorResponse.setCode("400");
    	errorResponse.setStatus("Request Failed");
      	errorResponse.setReason("Incoming payload is invalid");
      	BindingResult bindingResult = ex.getBindingResult();
      	List<ObjectError> errors = bindingResult.getAllErrors();
      	for(ObjectError error: errors) {
      		System.out.println(error.getDefaultMessage());
      		errorResponse.getErrorMessages().add(error.getDefaultMessage());
      	}
      	
    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
		ErrorResponse errorResponse = new ErrorResponse();
    	errorResponse.setCode("400");
    	errorResponse.setStatus("Request Failed");
      	errorResponse.setReason("Incoming path is invalid");
	     
	    ex.getConstraintViolations().forEach(cv -> {
	    	errorResponse.getErrorMessages().add(cv.getMessage());
	    }); 
	 
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
	
	

	 
	 @ExceptionHandler(TokenRefreshException.class)
		public ResponseEntity<TokenRefreshErrorResponse> handleTokenExpiredException(TokenRefreshException ex) {
			
			System.out.println("Token was expired. Inside ControllerExceptionManager....");
			
			TokenRefreshErrorResponse error = new TokenRefreshErrorResponse();
			error.setUuid("8DAAD377-9D8B-445E-950C-D5C29836217B");
		    TokenRefreshErrorStatusResponse errorStatus = new TokenRefreshErrorStatusResponse();
		    errorStatus.setErrorCode(5);
		    errorStatus.setErrorMessage("Expired refresh token");
		    error.setStatus(errorStatus);
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
		}
}
