/*package com.simpledevicedatabase.simpleddb.domain;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import org.springframework.hateoas.ResourceSupport;
import com.simpledevicedatabase.simpleddb.web.UserRestController;
import com.simpledevicedatabase.simpleddb.web.UserRoleRestController;
import lombok.Getter;

@Getter
public class UserRoleResource extends ResourceSupport {
    private final UserRole userRole;

    public UserRoleResource(final UserRole userRole) {
        this.userRole = userRole;
        final Long id = userRole.getId();
        add(linkTo(UserRoleRestController.class).withRel("userRoles"));
        //add(linkTo(methodOn(UserRoleRestController.class).all(id)).withRel("role"));
        add(linkTo(methodOn(UserRestController.class).get(id)).withSelfRel());
    }
}*/