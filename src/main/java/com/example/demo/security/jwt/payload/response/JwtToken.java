package com.example.demo.security.jwt.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtToken {
	
	 private String accessToken;
	 private String refreshToken;

}
