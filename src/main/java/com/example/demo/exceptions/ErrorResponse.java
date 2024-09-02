package com.example.demo.exceptions;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ErrorResponse {
	
	private String code = null;

	private String reason = null;

	private String status = null;
	
	private List<String> errorMessages = new ArrayList<>();

}
