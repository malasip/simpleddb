/*package com.simpledevicedatabase.simpleddb.web;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.simpledevicedatabase.simpleddb.domain.UserRepository;
import com.simpledevicedatabase.simpleddb.domain.UserRoleRepository;
import com.simpledevicedatabase.simpleddb.domain.UserRoleResource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/api/users/{id}/userRoles")
public class UserRoleRestController {
    
    @Autowired
    UserRoleRepository urrepository;

    @Autowired
    UserRepository urepository;

    @GetMapping
    public ResponseEntity<Resources<UserRoleResource>> all(@PathVariable Long id) {
        final List<UserRoleResource> collection = getUserRoles(id);
        final Resources<UserRoleResource> resources = new Resources<>(collection);
        final String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
        resources.add(new Link(uriString, "self"));
        return ResponseEntity.ok(resources);
    }

    private List<UserRoleResource> getUserRoles(Long id) {
        return urepository
            .findById(id)
            .map(
                u ->
                    StreamSupport.stream(u.getRole(), false)
                    .map(UserRoleResource::new)
                    .collect(Collectors.toList()))
        .orElseThrow(() -> new ResourceNotFoundException("Resource not found with id: " + id));
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserRoleResource> get(@PathVariable final Long id) {
        return urepository
            .findById(id)
            .map(u -> ResponseEntity.ok(new UserRoleResource(u)))
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }
}*/