package com.gfg.movieshark.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Value("${user.authority}")
    private String userAuthority;

    @Value("${admin.authority}")
    private String adminAuthority;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/user/signup/**").permitAll()
                        .requestMatchers("/show/add/**").hasAuthority(adminAuthority)
                        .requestMatchers("/movie/title/**").hasAnyAuthority(userAuthority, adminAuthority)
                        .requestMatchers("/movie/add/**").hasAuthority(adminAuthority)
                        .requestMatchers("/show/search/**").hasAnyAuthority(adminAuthority,userAuthority)
                        .requestMatchers("/review/add/**").hasAuthority(userAuthority)
                        .requestMatchers("/review/find/**").hasAnyAuthority(userAuthority, adminAuthority)
                        .requestMatchers("/theater/add/**").hasAuthority(adminAuthority)
                        .requestMatchers("/ticket/book/**").hasAnyAuthority(userAuthority, adminAuthority)
                        .anyRequest().permitAll() // Added permitAll() for all other requests
                ).formLogin(withDefaults())
                .httpBasic(withDefaults()).csrf(csrf ->csrf.disable());
        return http.build();
    }
}
