package com.simpledevicedatabase.simpleddb;

import com.simpledevicedatabase.simpleddb.domain.UserDetailServiceImpl;

import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.http.HttpMethod;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/dashboard/admin/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/devices/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/devices/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/api/devices/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/devices/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/deviceModels/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/deviceModels/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/api/deviceModels/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/deviceModels/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/deviceTypes/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/deviceTypes/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/api/deviceTypes/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/deviceTypes/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/users/1").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/users").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/users/*").fullyAuthenticated()
                .antMatchers(HttpMethod.POST, "/api/users/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/users/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/users/**").hasAuthority("ADMIN")
                .anyRequest().fullyAuthenticated()
                .and()
            .formLogin()
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