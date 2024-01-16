package com.todocine.configuration;

import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Arrays;
import java.util.stream.Collectors;


@Configuration
@EnableWebSecurity
public class WebConfiguration {

    private Logger logger = LoggerFactory.getLogger(WebConfiguration.class);

    @Value("${permit.all.resources}")
    private String[] resources;

    @Value("${permit.all.paths}")
    private String[] paths;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        logger.info(Arrays.stream(resources).collect(Collectors.toList()).toString());
        logger.info(Arrays.stream(paths).collect(Collectors.toList()).toString());

        AuthenticationConfiguration authenticationManagerConfiguration = http.getSharedObject(AuthenticationConfiguration.class);

        http.cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(request -> request
                        .requestMatchers(resources).permitAll()
                        .requestMatchers(paths).permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form.loginPage("/")
                        .loginProcessingUrl("/login")
                        .failureUrl("/login?error=true"))
                .logout(logout -> logout.logoutUrl("/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                    response.setStatus(HttpServletResponse.SC_OK);
                }))
                .addFilter(new JWTAuthenticationFilter(authenticationManager(authenticationManagerConfiguration)))
                .addFilter(new JWTAuthorisationFilter(authenticationManager(authenticationManagerConfiguration)));

        return http.build();

    }


}