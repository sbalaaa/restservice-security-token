package com.example.demo.security.jwt.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JwtResponse {
	private String uuid;
	private JwtTokenResponse response;
  
}
