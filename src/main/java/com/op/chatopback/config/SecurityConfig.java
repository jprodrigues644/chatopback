package com.op.chatopback.config;

import com.op.chatopback.security.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
/**
 * Security configuration for the application.
 * <p>
 * Configures Spring Security with JWT-based stateless authentication,
 * CORS policy, and defines public/protected endpoints.
 * </p>
 */
@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }
    /**
     * Configures the security filter chain.
     *
     * @param http the HttpSecurity to modify
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests( auth ->auth
                        .requestMatchers("/api/auth/register" ,
                                "/api/auth/login",
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**" ,
                                "/uploads/**" )
                        .permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(corsFilter(), ChannelProcessingFilter.class)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }
     
    /**
     * Provides the AuthenticationManager bean.
     *
     * @param configuration the AuthenticationConfiguration
     * @return the AuthenticationManager
     * @throws Exception if an error occurs while retrieving the manager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * Provides the BCryptPasswordEncoder bean for password hashing.
     *
     * @return the BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    /**
     * Configures CORS settings for the application.
     * Permit all origins, headers, and methods for development purposes allowed, adjust for production as needed.
     * @return the CorsFilter
     */
    @Bean
     public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }


}
