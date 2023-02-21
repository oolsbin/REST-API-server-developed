package com.example.demo.Jwt.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class TokenInfo {
 
    private String grantType;
    private String accessToken;
    private String refreshToken;
}