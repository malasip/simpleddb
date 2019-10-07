package com.simpledevicedatabase.simpleddb;

import com.simpledevicedatabase.simpleddb.domain.UserDetailServiceImpl;

import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.http.HttpMethod;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/dashboard/admin/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/device**").hasAuthority("ADMIN")
                //.antMatchers(HttpMethod.POST, "/api/deviceTypes/**").hasAuthority("ADMIN")
                //.antMatchers(HttpMethod.POST, "/api/devices/**").hasAuthority("ADMIN")
                .antMatchers("/api/user**").hasAuthority("ADMIN")
                //.antMatchers("/dashboard/modal/deviceModal").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/dashboard/modal/deviceModal")
                .permitAll()
                .and()
            .logout()
                .permitAll();
    }

    @Autowired
    private UserDetailServiceImpl userDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
}