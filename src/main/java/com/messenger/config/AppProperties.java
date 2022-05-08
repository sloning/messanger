package com.messenger.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private Auth auth;
    private Database database;

    @Getter
    @Setter
    public static class Auth {
        private String tokenSecret;
        private Long tokenExpirationMSec;
        private String tokenPrefix;
        private String headerString;
    }

    @Getter
    @Setter
    public static class Database {
        private String elasticHost;
    }
}
