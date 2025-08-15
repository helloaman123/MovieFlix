package com.movieflix.auth.utils;

public class LoginRequest {
   
	 private String email;
	 private String password;
	public LoginRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LoginRequest(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	  public static class Builder {
	        private String email;
	        private String password;

	        public Builder email(String email) {
	            this.email = email;
	            return this;
	        }

	        public Builder password(String password) {
	            this.password = password;
	            return this;
	        }

	        public LoginRequest build() {
	            return new LoginRequest(email, password);
	        }
	    }

	    // Static method to easily start building
	    public static Builder builder() {
	        return new Builder();
	    }
}
