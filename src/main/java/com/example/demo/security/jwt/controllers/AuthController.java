package com.example.demo.security.jwt.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.security.jwt.entities.ERole;
import com.example.demo.security.jwt.entities.RefreshToken;
import com.example.demo.security.jwt.entities.Role;
import com.example.demo.security.jwt.entities.User;
import com.example.demo.security.jwt.exception.TokenRefreshException;
import com.example.demo.security.jwt.payload.request.LogOutRequest;
import com.example.demo.security.jwt.payload.request.LoginRequest;
import com.example.demo.security.jwt.payload.request.SignupRequest;
import com.example.demo.security.jwt.payload.request.TokenRefreshRequest;
import com.example.demo.security.jwt.payload.response.JwtResponse;
import com.example.demo.security.jwt.payload.response.JwtToken;
import com.example.demo.security.jwt.payload.response.JwtTokenResponse;
import com.example.demo.security.jwt.payload.response.MessageResponse;
import com.example.demo.security.jwt.repository.RoleRepository;
import com.example.demo.security.jwt.repository.UserRepository;
import com.example.demo.security.jwt.JwtUtils;
import com.example.demo.security.jwt.services.RefreshTokenService;
import com.example.demo.security.jwt.services.UserDetailsImpl;



@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @Autowired
  RefreshTokenService refreshTokenService;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    String jwt = jwtUtils.generateJwtToken(userDetails);

    //List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
    //    .collect(Collectors.toList());
    
   // System.out.println("Assigned roles to the user is -> " + roles);

    RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
    
    JwtToken token = new JwtToken();
    token.setAccessToken(jwt);
    token.setRefreshToken(refreshToken.getToken());
    
    JwtTokenResponse tokenResponse = new JwtTokenResponse();
    tokenResponse.setResponseCode(0F);
    tokenResponse.setResponseMessage("SUCCESS");
    tokenResponse.setResponse(token);
   

    return ResponseEntity.ok(new JwtResponse("#UIID#",tokenResponse ));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
    }

    // Create new user's account
    User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
        encoder.encode(signUpRequest.getPassword()));

    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
        case "admin":
          Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(adminRole);
          break;
        case "user":
          Role userRole = roleRepository.findByName(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(userRole);
          break;
        case "create":
            Role createRole = roleRepository.findByName(ERole.ROLE_CREATE)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(createRole);
            break;
        case "update":
            Role updateRole = roleRepository.findByName(ERole.ROLE_UPDATE)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(updateRole);
            break;
        case "delete":
            Role deleteRole = roleRepository.findByName(ERole.ROLE_DELETE)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(deleteRole);
            break;
        default:
          Role defaultUserRole = roleRepository.findByName(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(defaultUserRole);
        }
      });
    }

    user.setRoles(roles);
    userRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }

  @PostMapping("/refreshtokenv1")
  public ResponseEntity<?> refreshtokenv1(@Valid @RequestBody TokenRefreshRequest request) {
    String requestRefreshToken = request.getRefreshToken();

    /*return refreshTokenService.findByToken(requestRefreshToken)
        .map(refreshTokenService::verifyExpiration)
        .map(RefreshToken::getUser)
        .map(user -> {
          String token = jwtUtils.generateTokenFromUsername(user.getUsername());
          return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
        })
        .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
            "Refresh token is not in database!"));*/
   
    String jwt = jwtUtils.generateTokenFromUsername("mod");
    
    JwtToken token = new JwtToken();
    token.setAccessToken(jwt);
    token.setRefreshToken(requestRefreshToken);
    
    JwtTokenResponse tokenResponse = new JwtTokenResponse();
    tokenResponse.setResponseCode(0F);
    tokenResponse.setResponseMessage("SUCCESS");
    tokenResponse.setResponse(token);
    
    return ResponseEntity.ok(new JwtResponse("#UIID#",tokenResponse ));
  }
  
  
  @PostMapping("/refreshtoken")
  public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
    String requestRefreshToken = request.getRefreshToken();
    RefreshToken refreshToken = refreshTokenService.findByToken(requestRefreshToken).orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
            "Refresh token is not in database!"));
    refreshToken = refreshTokenService.verifyExpiration(refreshToken);
    String jwt = jwtUtils.generateTokenFromUsername(refreshToken.getUser().getUsername());

    JwtToken token = new JwtToken();
    token.setAccessToken(jwt);
    token.setRefreshToken(requestRefreshToken);
    
    JwtTokenResponse tokenResponse = new JwtTokenResponse();
    tokenResponse.setResponseCode(0F);
    tokenResponse.setResponseMessage("SUCCESS");
    tokenResponse.setResponse(token);
    
    return ResponseEntity.ok(new JwtResponse("#UIID#",tokenResponse ));
  }
  
  @PostMapping("/logout")
  public ResponseEntity<?> logoutUser(@Valid @RequestBody LogOutRequest logOutRequest) {
    refreshTokenService.deleteByUserId(logOutRequest.getUserId());
    return ResponseEntity.ok(new MessageResponse("Log out successful!"));
  }

}
