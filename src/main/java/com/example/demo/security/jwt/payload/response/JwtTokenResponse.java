package com.example.demo.security.jwt.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtTokenResponse {

	private String responseMessage;
	private float responseCode;
	private JwtToken  response;
	
}
