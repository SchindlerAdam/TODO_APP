package com.schindler.todoapp.security;

import hu.progmasters.dailybugle.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private PasswordEncoder passwordEncoder;
    private ProfileService profileService;



    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder, ProfileService profileService) {
        this.passwordEncoder = passwordEncoder;

        this.profileService = profileService;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                    .cors()
                .and()
                    .csrf().disable()
                    .httpBasic()
                .and()
                    .formLogin()
                .and()
                    .authorizeRequests()
                    .antMatchers("/api/profile/register").permitAll()
                    .antMatchers("/api/article/list").permitAll()
                    .antMatchers("/api/article/get/**").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .logout()
                    .deleteCookies("JSESSIONID")
                    .invalidateHttpSession(true)
                    .clearAuthentication(true);


    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(profileService).passwordEncoder(passwordEncoder);
    }




}
