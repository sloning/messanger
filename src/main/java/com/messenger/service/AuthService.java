package com.messenger.service;

import com.messenger.dto.mapper.UserMapper;
import com.messenger.dto.model.LoginDto;
import com.messenger.dto.model.PasswordChangeDto;
import com.messenger.dto.model.RegisterDto;
import com.messenger.exception.EntityAlreadyExistsException;
import com.messenger.exception.EntityNotFoundException;
import com.messenger.model.User;
import com.messenger.security.AuthenticationFacade;
import com.messenger.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationFacade authenticationFacade;

    public Map<String, String> registerUser(RegisterDto registerDto) {
        if (userService.isUserExists(registerDto.getUsername())) {
            throw new EntityAlreadyExistsException(
                    String.format("User with email: %s already exists", registerDto.getUsername())
            );
        }

        User user = userMapper.createFrom(registerDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userService.save(user);

        return getToken(user);
    }

    public Map<String, String> loginUser(LoginDto loginDto) {
        User user = userService.findByUsername(loginDto.getUsername());

        checkPasswords(user.getPassword(), loginDto.getPassword());

        return getToken(user);
    }

    private boolean isPasswordsNotEqual(String dbPassword, String password) {
        return !passwordEncoder.matches(password, dbPassword);
    }

    private void checkPasswords(String dbPassword, String password) {
        if (isPasswordsNotEqual(dbPassword, password)) {
            throw new EntityNotFoundException("Wrong password");
        }
    }

    public Map<String, String> updatePassword(PasswordChangeDto passwordChangeDto) {
        User user = userService.findById(authenticationFacade.getUserId());

        checkPasswords(user.getPassword(), passwordChangeDto.getOldPassword());

        user.setPassword(passwordEncoder.encode(passwordChangeDto.getNewPassword()));
        user.setVersion(user.getVersion() + 1);
        user = userService.save(user);

        return getToken(user);
    }

    public Map<String, String> getToken(User user) {
        String token = jwtTokenProvider.createToken(user.getId(), user.getVersion().toString());

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("userId", user.getId());
        return response;
    }
}
