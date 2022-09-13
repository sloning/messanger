package com.messenger.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.messenger.config.AppProperties;
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
    private final AppProperties appProperties;
    private Algorithm algorithm;

    public JwtTokenProvider(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @PostConstruct
    public void init() {
        algorithm = Algorithm.HMAC256(appProperties.getAuth().getTokenSecret());
    }

    public String createToken(Long id, String version) {
        Date expirationDate = new Date(new Date().getTime() + appProperties.getAuth().getTokenExpirationMSec());

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", id);
        claims.put("version", version);

        return JWT.create()
                .withPayload(claims)
                .withIssuer(ISSUER)
                .withExpiresAt(expirationDate)
                .sign(algorithm);
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader(appProperties.getAuth().getHeaderString());
        if (bearerToken != null && bearerToken.startsWith(appProperties.getAuth().getTokenPrefix())) {
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

    public Long getUserId(Map<String, Claim> claims) {
        return claims.get("id").asLong();
    }

    public Long getUserVersion(Map<String, Claim> claims) {
        return Long.parseLong(claims.get("version").asString());
    }

    public JwtUser getJwtUser(String token) {
        Map<String, Claim> claims = getClaims(token);
        return JwtUserFactory.create(getUserId(claims), getUserVersion(claims));
    }

    public Authentication getAuthentication(String token) {
        JwtUser userDetails = getJwtUser(token);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}
