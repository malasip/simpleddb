package com.simpledevicedatabase.simpleddb.web;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import javax.validation.Valid;

import com.simpledevicedatabase.simpleddb.domain.User;
import com.simpledevicedatabase.simpleddb.domain.UserRepository;
import com.simpledevicedatabase.simpleddb.domain.UserRole;
import com.simpledevicedatabase.simpleddb.domain.UserRoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/users")
public class UserRestController {

    @Autowired UserRepository userRepository;
    @Autowired UserRoleRepository roleRepository;

    @PostMapping(produces = "application/json")
    ResponseEntity<User> addUser(@Valid @RequestBody User user) {
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    @PatchMapping(produces = "application/json")
    ResponseEntity<User> saveUser(@Valid @RequestBody User user) {
        return ResponseEntity.ok(user);
    }

    @GetMapping(produces = { "application/hal+json" })
    Resources<User> getUsers() {
        List<User> allUsers = userRepository.findAll();

        for (User user : allUsers) {
            Long userId = user.getUserId();
            Link selfLink = linkTo(UserRestController.class).slash(userId).withSelfRel();
            Link roleLink = linkTo(methodOn(UserRestController.class).getUserRole(userId)).withRel("role");
            user.add(selfLink);
            user.add(roleLink);
        }
        Link link = linkTo(UserRestController.class).withSelfRel();
        Resources<User> result = new Resources<User>(allUsers, link);
        return result;
    }

    @GetMapping(value = "/{id}/role", produces = { "application/hal+json" })
    Resource<UserRole> getUserRole(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invalid ID: " + id));
        UserRole role = user.getRole();
        Link selfLink = linkTo(methodOn(UserRoleRestController.class).getRole(role.getRoleId())).withSelfRel();
        role.add(selfLink);
        Resource<UserRole> result = new Resource<UserRole>(role);
        return result;
    }
}