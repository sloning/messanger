package com.messenger.security.jwt;

import com.messenger.model.User;

public class JwtUserFactory {

    private JwtUserFactory() {
        throw new IllegalStateException("Factory class");
    }

    public static JwtUser create(User user) {
        return new JwtUser(user.getId(), user.getUsername(), user.getPassword(), user.getVersion());
    }

    public static JwtUser create(Long id, Long version) {
        return new JwtUser(id, null, null, version);
    }
}
