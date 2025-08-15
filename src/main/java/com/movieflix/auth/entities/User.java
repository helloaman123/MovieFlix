package com.movieflix.auth.entities;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name ="Users")
public class User implements UserDetails{

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(Integer userId, String name, String username, String email, String password, UserRole role) {
		super();
		this.userId = userId;
		this.name = name;
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	
	@NotBlank(message ="This field can't be blank")
	private String name;
	
	@NotBlank(message ="This field can't be blank")
	@Column(unique = true)
	private String username;
	
	@NotBlank(message ="This field can't be blank")
	@Column(unique = true)
	@Email(message = "please enter emaill in proper format")
	private String email;
	
	
	@NotBlank(message ="This field can't be blank")
	@Size(min = 5 , message = "The password must have 5 characters")
	private String password;
	
	@Enumerated(EnumType.STRING)
	private UserRole role;
	
	@OneToOne(mappedBy = "user")
	private RefreshToken refreshToken;
	
	
	public RefreshToken getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(RefreshToken refreshToken) {
		this.refreshToken = refreshToken;
	}

	private boolean isEnabled = true;
	
	private boolean isAccountNonExpired=true;
	
	private boolean isAccountNonLocked=true;
	
	private boolean isCredentialsNonExpired=true;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}
    public static class Builder {
        private Integer userId;
        private String name;
        private String username;
        private String email;
        private String password;
        private UserRole role;
        private RefreshToken refreshToken;
        private boolean isEnabled = true;
        private boolean isAccountNonExpired = true;
        private boolean isAccountNonLocked = true;
        private boolean isCredentialsNonExpired = true;

        public Builder userId(Integer userId) {
            this.userId = userId;
            return this;
        }
        public Builder name(String name) {
            this.name = name;
            return this;
        }
        public Builder username(String username) {
            this.username = username;
            return this;
        }
        public Builder email(String email) {
            this.email = email;
            return this;
        }
        public Builder password(String password) {
            this.password = password;
            return this;
        }
        public Builder role(UserRole role) {
            this.role = role;
            return this;
        }
        public Builder refreshToken(RefreshToken refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }
        public Builder enabled(boolean enabled) {
            this.isEnabled = enabled;
            return this;
        }
        public Builder accountNonExpired(boolean accountNonExpired) {
            this.isAccountNonExpired = accountNonExpired;
            return this;
        }
        public Builder accountNonLocked(boolean accountNonLocked) {
            this.isAccountNonLocked = accountNonLocked;
            return this;
        }
        public Builder credentialsNonExpired(boolean credentialsNonExpired) {
            this.isCredentialsNonExpired = credentialsNonExpired;
            return this;
        }
        public User build() {
            return new User(userId, name, username, email, password, role);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
