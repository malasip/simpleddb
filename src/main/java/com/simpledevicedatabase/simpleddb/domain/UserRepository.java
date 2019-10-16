package com.simpledevicedatabase.simpleddb.domain;

import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.repository.Repository;
//import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;

public interface UserRepository extends CrudRepository<User, Long> {
    
    @Override
    @PreAuthorize("hasRole('ADMIN') or #username == authentication.principal.username")
    User save(User user);

    User findByUsername(String username);
}