package com.jeonghyeon.blogapi.security.config;

import com.jeonghyeon.blogapi.security.auth.PrincipalDetailsService;
import com.jeonghyeon.blogapi.security.jwt.JwtAccessDeniedHandler;
import com.jeonghyeon.blogapi.security.jwt.JwtAuthenticationEntryPoint;
import com.jeonghyeon.blogapi.security.jwt.JwtSecurityConfig;
import com.jeonghyeon.blogapi.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig{


    private final TokenProvider tokenProvider;

    private final PrincipalDetailsService principalDetailsService;

    private final CorsFilter corsFilter;

    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;


    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf().disable();

        http.addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class);

        http.formLogin().disable();

        http.authorizeRequests().antMatchers(
                "/v2/api-docs",
                "/swagger-ui.html",
                        "/webjars/**",
                        "/swagger-resources/**",
                        "/swagger/**",
                        "/h2-console/**",
                        "/api/**").permitAll()
                .anyRequest().authenticated();

        http.headers().frameOptions().disable();

        http.exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler);

        http.userDetailsService(principalDetailsService);

        http.apply(new JwtSecurityConfig(tokenProvider));

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
}
