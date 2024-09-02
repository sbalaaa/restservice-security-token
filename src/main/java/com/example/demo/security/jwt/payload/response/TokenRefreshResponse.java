package com.example.demo.security.jwt.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenRefreshResponse {
  private String token;
  private String refreshToken;
  private String tokenType = "Bearer";

  public TokenRefreshResponse(String token, String refreshToken) {
    this.token = token;
    this.refreshToken = refreshToken;
  }

}
