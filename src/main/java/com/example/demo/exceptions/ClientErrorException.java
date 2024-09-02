package com.example.demo.exceptions;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ClientErrorException extends RuntimeException {
	
	
	
	public ClientErrorException(String message) {
		super(message);
	}

}
