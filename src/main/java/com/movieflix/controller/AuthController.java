package com.movieflix.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movieflix.auth.entities.RefreshToken;
import com.movieflix.auth.entities.User;
import com.movieflix.auth.services.AuthService;
import com.movieflix.auth.services.JwtService;
import com.movieflix.auth.services.RefreshTokenService;
import com.movieflix.auth.utils.AuthResponse;
import com.movieflix.auth.utils.LoginRequest;
import com.movieflix.auth.utils.RefreshTokenRequest;
import com.movieflix.auth.utils.RegisterRequest;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	private final AuthService authService;
	private final RefreshTokenService refreshService;
	private final JwtService jwtService;

	public AuthController(AuthService authService,
			RefreshTokenService refreshService
			,JwtService jwtService) {
		this.authService = authService;
		this.refreshService = refreshService;
		this.jwtService= jwtService;
	}
	
	@PostMapping("/register")
	public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest){
		return ResponseEntity.ok(authService.register(registerRequest));
	}
	
	@PostMapping("login")
	public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest){
		return ResponseEntity.ok(authService.login(loginRequest));
	}
	@PostMapping("/refresh")
	public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest){
		RefreshToken refreshToken = refreshService.verifyRefreshToken(refreshTokenRequest.getRefreshToken());
		User user = refreshToken.getUser();
		 var accessToken = jwtService.generateToken(user);
		 
		 return ResponseEntity.ok(AuthResponse.builder()
				 .accessToken(accessToken)
				 .refreshToken(refreshToken.getRefreshToken())
				 .build());
		 
	}
	
}
