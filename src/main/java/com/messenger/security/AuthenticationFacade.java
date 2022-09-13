package com.messenger.security;

import com.messenger.exception.EntityNotFoundException;
import com.messenger.security.jwt.JwtUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationFacade {
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Long getUserId() {
        Authentication auth = getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            JwtUser jwtUser = (JwtUser) auth.getPrincipal();
            return jwtUser.getId();
        }
        throw new EntityNotFoundException("Forbidden");
    }
}
