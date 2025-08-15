package com.movieflix.auth.utils;

import lombok.Builder;


public class RegisterRequest {

	private String name;
	private String email;
	public RegisterRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public RegisterRequest(String name, String email, String username, String password) {
		super();
		this.name = name;
		this.email = email;
		this.username = username;
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	private String username;
	private String password;
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	  public static class Builder {
	        private String name;
	        private String email;
	        private String username;
	        private String password;

	        public Builder name(String name) {
	            this.name = name;
	            return this;
	        }
	        public Builder email(String email) {
	            this.email = email;
	            return this;
	        }
	        public Builder username(String username) {
	            this.username = username;
	            return this;
	        }
	        public Builder password(String password) {
	            this.password = password;
	            return this;
	        }
	        public RegisterRequest build() {
	            return new RegisterRequest(name, email, username, password);
	        }
	    }

	    // Static helper to easily start a builder
	    public static Builder builder() {
	        return new Builder();
	    }
}
