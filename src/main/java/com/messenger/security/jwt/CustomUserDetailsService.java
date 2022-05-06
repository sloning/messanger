package com.messenger.security.jwt;

import com.messenger.exception.EntityNotFoundException;
import com.messenger.model.User;
import com.messenger.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User not found with email : " + email));

        return JwtUserFactory.create(user);
    }

    public UserDetails loadUserById(String id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("User with id: %s was not found", id)));

        return JwtUserFactory.create(user);
    }
}
