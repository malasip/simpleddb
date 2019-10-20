package com.simpledevicedatabase.simpleddb.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

import javax.persistence.CascadeType;


@Entity
@Table(name="user_role")
public class UserRole extends ResourceSupport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @NotEmpty(message = "Name is required")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "role")
    private List<User> users;

    public UserRole() {}

    public UserRole(String name) {
        this.name = name;
    }
    /**
     * @return the id
     */
    public Long getRoleId() {
        return roleId;
    }
    /**
     * @param id the id to set
     */
    public void setRoleId(Long id) {
        this.roleId = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return super.toString();
    }
}