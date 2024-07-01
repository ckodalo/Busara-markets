package com.predictions.predictions.config;

import com.predictions.predictions.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/home", "/markets", "/predict", "/user/signup", "/css/**", "/js/**").permitAll()
                        .anyRequest().authenticated()

                )
                .formLogin((form) -> form
                        .loginPage("/user/login")
//                        .defaultSuccessUrl("/markets", true)
                        .permitAll()


                )
                .logout((logout) -> logout
//                        .logoutUrl("/user/logout")
                                .permitAll()
                                .deleteCookies("JSESSIONID")
//                        .logoutSuccessUrl("/markets")
//                        .invalidateHttpSession(true)

                )
                .sessionManagement((sessionManagement) -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)

                );
//                .requiresChannel((requiresChannel) -> requiresChannel
//                        .requestMatchers("/user/login").requiresSecure()
//                );
//                .csrf(AbstractHttpConfigurer::disable
//                );


        return http.build();
    }

    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

