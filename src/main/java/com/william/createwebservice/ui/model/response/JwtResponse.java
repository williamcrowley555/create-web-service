package com.william.createwebservice.ui.model.response;

import com.william.createwebservice.security.SecurityConstants;

import java.util.List;

public class JwtResponse {

    private String token;
    private String type = SecurityConstants.TOKEN_PREFIX;
    private String userId;
    private String email;
    private List<String> roles;

    public JwtResponse(String token, String userId, String email, List<String> roles) {
        this.token = token;
        this.userId = userId;
        this.email = email;
        this.roles = roles;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return roles;
    }
}
