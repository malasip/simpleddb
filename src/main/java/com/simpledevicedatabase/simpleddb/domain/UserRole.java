package com.simpledevicedatabase.simpleddb.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.springframework.hateoas.ResourceSupport;


@Entity
@Table(name="user_role")
public class UserRole extends ResourceSupport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @NotEmpty(message = "Name is required")
    @Length(max = 10)
    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "role")
    private List<User> users;

    public UserRole() {}

    public UserRole(String name) {
        this.name = name;
    }

    //Getters
    public Long getRoleId() { return roleId; }
    public String getName() { return name; }
    //Setters
    public void setRoleId(Long id) { this.roleId = id; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return super.toString();
    }
}