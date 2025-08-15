package com.movieflix.auth.services;

import java.time.Instant;
import java.util.UUID;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.movieflix.auth.entities.RefreshToken;
import com.movieflix.auth.entities.User;
import com.movieflix.auth.repositories.RefreshTokenRepository;
import com.movieflix.auth.repositories.UserRepository;

@Service
public class RefreshTokenService {

	private final UserRepository userRepository;
	private final RefreshTokenRepository refreshTokenRepository;
	
	public RefreshTokenService(UserRepository userRepository,RefreshTokenRepository refreshTokenRepository) 
	{
		this.userRepository=userRepository;
		this.refreshTokenRepository=refreshTokenRepository;
	}
	
	public RefreshToken createRefreshToken(String username) {
		User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("No user found"));
	     RefreshToken refreshToken = user.getRefreshToken();
	     if(refreshToken == null) {
	    	 long refreshTokenValidity = 30*1000;
	    	 refreshToken = RefreshToken.builder()
	    			 .refreshToken(UUID.randomUUID().toString())
	    			 .expirationTime(Instant.now().plusMillis(refreshTokenValidity))
	    			 .user(user)
	    			 .build();
	    	 refreshTokenRepository.save(refreshToken);
	     }
	     return refreshToken;
	}
	
	public RefreshToken verifyRefreshToken(String refreshToken) {
	 RefreshToken reftoken =refreshTokenRepository.findByRefreshToken(refreshToken)
			 .orElseThrow(()-> new RuntimeException("No token found"));
	    if(reftoken.getExpirationTime().compareTo(Instant.now())<0) {
	    	
	    	refreshTokenRepository.delete(reftoken);
	    	throw new RuntimeException("Refresh token expired");
	    }
	    return reftoken;
	}
}
