package com.simpledevicedatabase.simpleddb.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        if (curruser == null) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
        UserDetails user = new org.springframework.security.core.userdetails.User(username, curruser.getPassword(),
        AuthorityUtils.createAuthorityList(curruser.getRole().getName()));
        if (!curruser.getActive()) {
            throw new DisabledException("The account has not been activated");
        }
        return user;
    }
}