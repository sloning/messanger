package com.messenger.security.jwt;

import com.messenger.model.User;

public class JwtUserFactory {

    private JwtUserFactory() {
        throw new IllegalStateException("Factory class");
    }

    public static JwtUser create(User user) {
        return new JwtUser(user.getId(), user.getUsername(), user.getPassword(), user.getVersion());
    }

    public static JwtUser create(String id, Long version) {
        return new JwtUser(id, id, null, version);
    }
}
