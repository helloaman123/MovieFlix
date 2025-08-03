package com.movieflix.auth.entities;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tokenId;

    @Column(nullable = false, length = 500)
    @NotNull(message = "Please enter refresh token value!")
    private String refreshToken;

    @Column(nullable = false)
    private Instant expirationTime;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;

    // No-args constructor
    public RefreshToken() {
        super();
    }

    // All-args constructor
    public RefreshToken(Integer tokenId, 
                        @NotNull(message = "please enter refresh token value!") String refreshToken,
                        Instant expirationTime,
                        User user) {
        super();
        this.tokenId = tokenId;
        this.refreshToken = refreshToken;
        this.expirationTime = expirationTime;
        this.user = user;
    }

    // Private constructor used by builder
    private RefreshToken(Builder builder) {
        this.tokenId = builder.tokenId;
        this.refreshToken = builder.refreshToken;
        this.expirationTime = builder.expirationTime;
        this.user = builder.user;
    }

    // Getters and setters
    public Integer getTokenId() {
        return tokenId;
    }

    public void setTokenId(Integer tokenId) {
        this.tokenId = tokenId;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Instant getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Instant expirationTime) {
        this.expirationTime = expirationTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Static builder() method
    public static Builder builder() {
        return new Builder();
    }

    // Builder class
    public static class Builder {
        private Integer tokenId;
        private String refreshToken;
        private Instant expirationTime;
        private User user;

        public Builder tokenId(Integer tokenId) {
            this.tokenId = tokenId;
            return this;
        }

        public Builder refreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

        public Builder expirationTime(Instant expirationTime) {
            this.expirationTime = expirationTime;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public RefreshToken build() {
            return new RefreshToken(this);
        }
    }
}
