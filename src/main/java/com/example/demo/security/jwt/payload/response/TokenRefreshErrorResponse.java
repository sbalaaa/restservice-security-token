package com.example.demo.security.jwt.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenRefreshErrorResponse {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5159682792072167273L;

	private String uuid;
	
	private TokenRefreshErrorStatusResponse status;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public TokenRefreshErrorStatusResponse getStatus() {
		return status;
	}

	public void setStatus(TokenRefreshErrorStatusResponse status) {
		this.status = status;
	}
	
	
	
}
