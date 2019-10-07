package com.simpledevicedatabase.simpleddb.domain;

import org.springframework.data.repository.CrudRepository;

public interface UserRoleRepository extends CrudRepository<UserRole, Long> { 
    UserRole findByName(String name);
 }