package com.example.demo.security.jwt.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenRefreshErrorStatusResponse {
	
	private int errorCode;
	private String errorMessage;
	
}
