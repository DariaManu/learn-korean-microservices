package com.ubb.usermanagementservice.conf;

import com.ubb.usermanagementservice.service.HttpRequestsHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public HttpRequestsHandler httpRequestsHandler() {
        return new HttpRequestsHandler();
    }
}
