/*package com.simpledevicedatabase.simpleddb.web;

import com.simpledevicedatabase.simpleddb.domain.User;
import com.simpledevicedatabase.simpleddb.domain.UserRole;
import com.simpledevicedatabase.simpleddb.domain.UserRepository;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;
import java.util.Spliterator;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import com.simpledevicedatabase.simpleddb.domain.UserResource;
import java.net.URI;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(value = "/api/users", produces = "application/hal+json")
public class UserRestController {
    
    @Autowired
    UserRepository repository;

    @GetMapping
    public ResponseEntity<Resources<UserResource>> all() {
        final List<UserResource> collection =
            StreamSupport.stream(repository.findAll().spliterator(), false).map(UserResource::new).collect(Collectors.toList());
        final Resources<UserResource> resources = new Resources<>(collection);
        final String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
        resources.add(new Link(uriString, "self"));
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResource> get(@PathVariable final Long id) {
        return repository
            .findById(id)
            .map(u -> ResponseEntity.ok(new UserResource(u)))
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @PostMapping
    public ResponseEntity<UserResource> post(@RequestBody User requestUser) {
        final User user = new User(requestUser);
        repository.save(user);
        final URI uri = 
            MvcUriComponentsBuilder.fromController(getClass())
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.created(uri).body(new UserResource(user));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResource> patch(@PathVariable("id") final Long id, @RequestBody User requestUser) {
        final User user = new User(requestUser);
        repository.save(user);
        final UserResource resource = new UserResource(user);
        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(uri).body(resource);
    }


    /*@PatchMapping("/api/users/{id}")
    public HttpEntity<User> getUser(@PathVariable Long id) {
        User user = repository.findById(id).orElseThrow();
        //user.add(linkTo(methodOn(UserRestController.class).getUser(id)).withSelfRel());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}*/