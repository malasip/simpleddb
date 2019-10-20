package com.simpledevicedatabase.simpleddb.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.hateoas.ResourceSupport;

@Entity
@Table(name="user")
public class User extends ResourceSupport{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, updatable = false)
    private Long userId;

    @NotEmpty(message = "Username is requried")
    @Column(name = "username", nullable = false, updatable = false)
    private String username;

    @NotEmpty(message = "Password is required")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Email
    private String email;

    @ManyToOne
    @JoinColumn(name = "role_id")
	private UserRole role;

    @Column(name = "active", nullable = false)
    private boolean active = false;
    
    public User() {}

    public User(String username, String password, String email, UserRole role) {
        this.username = username;
        this.password = password;
        this.email= email;
        this.role = role;
    }
    public User(String username, String password, String email, UserRole role, boolean active) {
        this.username = username;
        this.password = password;
        this.email= email;
        this.role = role;
        this.active = active;
    }
    
    //Getters
    public Long getUserId() { return userId; }
    //public Link getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public UserRole getRole() { return role; }
    public String getEmail() { return email; }
    public boolean getActive() { return active; }
    public LocalDateTime getLastLogin() { return lastLogin; }
    //Setters
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setUserRole(UserRole role) { this.role = role; }
    public void setEmail(String email) { this.email = email; }
    public void setActive(boolean active) { this.active = active; }
    public void setLastLogin(LocalDateTime lastLogin) { this.lastLogin = lastLogin; }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return super.toString();
    }
}