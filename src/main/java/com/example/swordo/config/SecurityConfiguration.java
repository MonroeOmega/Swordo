package com.example.swordo.config;

import com.example.swordo.service.impl.CustomLogoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration{

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder encoder;
    private final CustomLogoutHandler logoutHandler;


    public SecurityConfiguration(UserDetailsService userDetailsService, PasswordEncoder encoder, CustomLogoutHandler logoutHandler) {
        this.userDetailsService = userDetailsService;
        this.encoder = encoder;
        this.logoutHandler = logoutHandler;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests((authorize) ->{
                    authorize
                            .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                            .requestMatchers("/","/user/login","/user/register","/user/login/error").anonymous()
                            .requestMatchers("/admin","/admin/*").hasRole("ADMIN")
                            .requestMatchers("/**").fullyAuthenticated();
                })
                .formLogin(form -> {
                    form
                            .loginPage("/user/login")
                            .usernameParameter("username")
                            .passwordParameter("password")
                            .defaultSuccessUrl("/user/login/load",true)
                            .failureForwardUrl("/user/login/error");
                })
                .logout(logout -> {
                    logout
                            .logoutUrl("/user/logout")
                            .addLogoutHandler(logoutHandler)
                            .logoutSuccessUrl("/")
                            .invalidateHttpSession(true);
                }).build();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(encoder);
    }


}
