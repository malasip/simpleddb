package com.simpledevicedatabase.simpleddb.web;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import java.util.List;

import com.simpledevicedatabase.simpleddb.domain.User;
import com.simpledevicedatabase.simpleddb.domain.UserRepository;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.security.access.prepost.PreAuthorize;

@RestController
public class UserController {

    private final UserRepository urepository;

    UserController(UserRepository urepository) {
        this.urepository = urepository;
    }
    /*
    @GetMapping("/api/users")
    ResponseEntity<CollectionModel<EntityModel<User>>> findAll() {
	    List<EntityModel<User>> users = StreamSupport.stream(urepository.findAll().spliterator(), false)
		.map(user -> new EntityModel<>(user,
			linkTo(methodOn(UserController.class).findOne(user.getId())).withSelfRel(),
			linkTo(methodOn(UserController.class).findAll()).withRel("users")))
		.collect(Collectors.toList());
	return ResponseEntity.ok(
		new CollectionModel<>(users,
			linkTo(methodOn(UserController.class).findAll()).withSelfRel()));
    }
    @GetMapping("/api/users/{id}")
    ResponseEntity<EntityModel<User>> findOne(@PathVariable Long id) {
        return urepository.findById(id)
            .map(user -> new EntityModel<>(user,
                linkTo(methodOn(UserController.class).findOne(user.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).findAll()).withRel("users")))
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }*/
    //@PreAuthorize("hasAuthority('ADMIN') or #username == authentication.principal.username")
    @PatchMapping("/api/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User modUser) throws ResourceNotFoundException {
        User user = urepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        user.setPassword(modUser.getPassword());
        user.setEmail(modUser.getEmail());
        user.setUserRole(modUser.getRole());
        user.setActive(modUser.getActive());
        final User updatedUser = urepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

}