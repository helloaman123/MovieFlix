package com.movieflix.auth.services;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
   private static final String SECERET_KEY = "w7Z2WxRpykGri1IYWigRi1b5VEJMMtLSaXX1MvVeGZc=";
   
   //extract username from JWT
   
   public String exctractUsername (String token) {
	   return extractClaim(token,Claims::getSubject);
   }

public <T> T extractClaim(String token, Function<Claims ,T> claimsResolver) {
	// TODO Auto-generated method stub
	final Claims claims = extractAllClaims(token);
	return claimsResolver.apply(claims);
}

//extract information from jwt
private Claims extractAllClaims(String token) {
	// TODO Auto-generated method stub
	token = token.trim();
	System.out.println("Parsing Token: '" + token + "'");
	return Jwts
			.parser()
			.setSigningKey(getSignInKey())
			.build()
			.parseClaimsJws(token)
			.getBody();
}

private Key getSignInKey() {
	// TODO Auto-generated method stub
	//decode seceret key
	byte[] keyBytes = Decoders.BASE64.decode(SECERET_KEY);
	return Keys.hmacShaKeyFor(keyBytes);
}
public String generateToken(UserDetails userDetails) {
	return generateToken(new HashMap(),userDetails);
}
//generate token using jwt utility class and return token as string
  @SuppressWarnings("deprecation")
public String generateToken(
		  Map<String ,Object> extraClaims,
		  UserDetails userDetails
		  ) {
	  return Jwts.builder()
			  .setClaims(extraClaims)
			  .setSubject(userDetails.getUsername())
			  .setIssuedAt(new Date(System.currentTimeMillis()))
			  .setExpiration(new Date(System.currentTimeMillis()+60*60*1000))
			  .signWith(getSignInKey(), SignatureAlgorithm.HS256)
			  .compact();
  }
  // if token is valid by checking if token is expired for current user
  
  public boolean isTokenValid(String token , UserDetails userDetails) {
	  final String username = exctractUsername(token);
	  return (username.equals(userDetails.getUsername())&&!isTokenExpired(token));
	  
  }
  // if token is expired
  
  private boolean isTokenExpired(String token) {
	  return extractExpiration(token).before(new Date());
  }
  // get expiration date from token
private Date extractExpiration(String token) {
	
	return extractClaim(token,Claims::getExpiration);
}
  
  

}
