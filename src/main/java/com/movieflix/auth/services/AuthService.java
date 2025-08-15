package com.movieflix.auth.services;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.movieflix.auth.config.ApplicationConfig;
import com.movieflix.auth.entities.User;
import com.movieflix.auth.entities.UserRole;
import com.movieflix.auth.repositories.UserRepository;
import com.movieflix.auth.utils.AuthResponse;
import com.movieflix.auth.utils.LoginRequest;
import com.movieflix.auth.utils.RegisterRequest;

@Service
public class AuthService {
     private final ApplicationConfig appconfig;
     private final PasswordEncoder passEncode;
     private final UserRepository userRepo;
     private final JwtService jwtService;
     private final RefreshTokenService refreshTokenService;
     private final AuthenticationManager authenticationManager;
     
	public AuthService(ApplicationConfig appconfig
			,PasswordEncoder passEncode,
			UserRepository userRepo,
			JwtService jwtService,
			RefreshTokenService refreshTokenService,
			AuthenticationManager authenticationManager) {
		this.appconfig= appconfig;
		this.passEncode = passEncode;
		this.userRepo = userRepo;
		this.jwtService= jwtService;
		this.refreshTokenService=refreshTokenService;
		this.authenticationManager=authenticationManager;
		
		
		// TODO Auto-generated constructor stub
	}

	public AuthResponse register(RegisterRequest registerRequest) {
		var user = User.builder()
				.name(registerRequest.getName())
				.email(registerRequest.getEmail())
				.username(registerRequest.getUsername())
				.password(passEncode.encode(registerRequest.getPassword()))
				.role(UserRole.USER)
				.build();
		User savedUser = userRepo.save(user);
	     var accessToken = jwtService.generateToken(savedUser);
	     var refreshToken = refreshTokenService.createRefreshToken(savedUser.getEmail());
	     
	     return AuthResponse.builder()
	    		 .accessToken(accessToken)
	    		 .refreshToken(refreshToken.getRefreshToken())
	    		 .build();
	}
	public AuthResponse login(LoginRequest loginRequest) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
			loginRequest.getEmail(),loginRequest.getPassword()));
		User user = userRepo.findByEmail(loginRequest.getEmail()).orElseThrow(()->new UsernameNotFoundException("No user with this email"));
	      var accessToken=jwtService.generateToken(user);
	      var refreshToken = refreshTokenService.createRefreshToken(loginRequest.getEmail());
	      
	      return AuthResponse.builder()
	    		  .accessToken(accessToken)
	    		  .refreshToken(refreshToken.getRefreshToken())
	    		  .build();
	}
}
