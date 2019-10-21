package com.simpledevicedatabase.simpleddb.web;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.List;

import com.simpledevicedatabase.simpleddb.domain.UserRole;
import com.simpledevicedatabase.simpleddb.domain.UserRoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/userRoles", produces = "application/hal+json")
public class UserRoleRestController {

    @Autowired UserRoleRepository repository;

    @GetMapping(produces = "application/hal+json")
    public Resources<UserRole> getAllRoles() {
        List<UserRole> allRoles = repository.findAll();

        for (UserRole role : allRoles) {
            Long roleId = role.getRoleId();
            Link selfLink = linkTo(UserRoleRestController.class).slash(roleId).withSelfRel();
            role.add(selfLink);
        }
        Link link = linkTo(UserRoleRestController.class).withSelfRel();
        Resources<UserRole> result = new Resources<UserRole>(allRoles, link);
        return result;
    }

    @GetMapping(value = "/{id}", produces = "application/hal+json")
    public Resource<UserRole> getRole(@PathVariable Long id) {
        UserRole role = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invalid ID:" + id));
        Link selfLink = linkTo(UserRoleRestController.class).slash(id).withSelfRel();
        role.add(selfLink);
        Link link = linkTo(UserRoleRestController.class).withSelfRel();
        Resource<UserRole> result = new Resource<UserRole>(role, link);
        return result;
    }
}