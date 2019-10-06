package com.simpledevicedatabase.simpleddb.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepository urepository;
    @Autowired
    public UserDetailServiceImpl(UserRepository userRepository) {
        this.urepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User curruser = urepository.findByUsername(username);
        if (!curruser.getActive()) {
            throw new DisabledException("The account has not been activated");
        }
        UserDetails user = new org.springframework.security.core.userdetails.User(username, curruser.getPasswordHash(),
        AuthorityUtils.createAuthorityList(curruser.getRole()));
        return user;
    }
}