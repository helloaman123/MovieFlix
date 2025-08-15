package com.movieflix.auth.utils;

public class AuthResponse {
 private String refreshToken;

 
 private String accessToken;


public AuthResponse() {
	super();
	// TODO Auto-generated constructor stub
}


public AuthResponse(String refreshToken, String accessToken) {
	super();
	this.refreshToken = refreshToken;
	this.accessToken = accessToken;
}


public String getRefreshToken() {
	return refreshToken;
}


public void setRefreshToken(String refreshToken) {
	this.refreshToken = refreshToken;
}


public String getAccessToken() {
	return accessToken;
}


public void setAccessToken(String accessToken) {
	this.accessToken = accessToken;
}

public static class Builder {
    private String refreshToken;
    private String accessToken;

    public Builder refreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    public Builder accessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public AuthResponse build() {
        return new AuthResponse(refreshToken, accessToken);
    }
}

// Static helper to easily start building
public static Builder builder() {
    return new Builder();
}
 
}
