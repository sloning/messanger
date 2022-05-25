package com.messenger.security;

import com.messenger.config.AppProperties;
import com.messenger.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthChannelInterceptorAdapter implements ChannelInterceptor {

    private static final String AUTHENTICATION_HEADER = "Authorization";
    private final JwtTokenProvider jwtTokenProvider;
    private final AppProperties appProperties;

    @Override
    public Message<?> preSend(final Message<?> message, final MessageChannel channel) throws AuthenticationException {
        final StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        String token = accessor.getFirstNativeHeader(AUTHENTICATION_HEADER);
        if (token != null && token.startsWith(appProperties.getAuth().getTokenPrefix())) {
            token = token.substring(7);
        }

        if (token != null && jwtTokenProvider.verifyToken(token)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            accessor.setUser(authentication);
        }

        return message;
    }
}
