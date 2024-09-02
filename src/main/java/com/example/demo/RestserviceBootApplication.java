package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan({"com.example.demo.security.jwt,com.example.demo.controllers,com.example.demo.security.jwt.controllers,com.example.demo.security.jwt.services,com.example.demo.security.jwt.repository"})
public class RestserviceBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestserviceBootApplication.class, args);
	}

}
