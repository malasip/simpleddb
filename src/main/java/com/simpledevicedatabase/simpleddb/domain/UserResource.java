/*package com.simpledevicedatabase.simpleddb.domain;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import org.springframework.hateoas.ResourceSupport;
import com.simpledevicedatabase.simpleddb.web.UserRestController;
//import com.simpledevicedatabase.simpleddb.web.UserRoleRestController;
import lombok.Getter;

@Getter
public class UserResource extends ResourceSupport {
    private final User user;

    public UserResource(final User user) {
        this.user = user;
        final Long id = user.getId();
        add(linkTo(UserRestController.class).withRel("users"));
        //add(linkTo(methodOn(UserRoleRestController.class).all(id)).withRel("role"));
        add(linkTo(methodOn(UserRestController.class).get(id)).withSelfRel());
    }
}*/