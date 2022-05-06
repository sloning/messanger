package com.messenger.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.messenger.config.SecurityProperties;
import com.messenger.exception.JwtAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {

    private static final String ISSUER = "messenger";
    private final SecurityProperties securityProperties;
    private Algorithm algorithm;

    public JwtTokenProvider(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @PostConstruct
    public void init() {
        algorithm = Algorithm.HMAC256(securityProperties.getAuth().getTokenSecret());
    }

    public String createToken(String id, String version) {
        Date expirationDate = new Date(new Date().getTime() + securityProperties.getAuth().getTokenExpirationMSec());

        Map<String, String> claims = new HashMap<>();
        claims.put("id", id);
        claims.put("version", version);

        return JWT.create()
                .withPayload(claims)
                .withIssuer(ISSUER)
                .withExpiresAt(expirationDate)
                .sign(algorithm);
    }

    public String refreshToken(String token) {
        Map<String, Claim> claims = getClaims(token);
        Map<String, String> stringClaims = new HashMap<>();
        for (Map.Entry<String, Claim> entry : claims.entrySet()) {
            if (stringClaims.put(entry.getKey(), entry.getValue().asString()) != null) {
                throw new IllegalStateException("Duplicate key");
            }
        }
        Date expirationDate = new Date(new Date().getTime() + securityProperties.getAuth().getTokenExpirationMSec());

        return JWT.create()
                .withPayload(stringClaims)
                .withIssuer(ISSUER)
                .withExpiresAt(expirationDate)
                .sign(algorithm);
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader(securityProperties.getAuth().getHeaderString());
        if (bearerToken != null && bearerToken.startsWith(securityProperties.getAuth().getTokenPrefix())) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean verifyToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            throw new JwtAuthenticationException("Invalid session, please log in again");
        }
    }

    public Map<String, Claim> getClaims(String token) {
        return JWT.decode(token).getClaims();
    }

    public String getUserId(Map<String, Claim> claims) {
        return claims.get("id").asString();
    }

    public Long getUserVersion(Map<String, Claim> claims) {
        return Long.parseLong(claims.get("version").asString());
    }

    public Authentication getAuthentication(String token) {
        Map<String, Claim> claims = getClaims(token);
        JwtUser userDetails = JwtUserFactory.create(getUserId(claims), getUserVersion(claims));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}
