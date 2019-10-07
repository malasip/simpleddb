package com.simpledevicedatabase.simpleddb.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "username", nullable = false, updatable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "lastLogin", nullable = true)
    private String lastLogin = "Never";

    @Column(name = "email", nullable = true)
    private String email;

    @ManyToOne
	@JoinColumn(name = "roleName")
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

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }
    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }
    /**
     * @param password the passwordHash to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * @return the role
     */
    public UserRole getRole() {
        return role;
    }
    /**
     * @param role the role to set
     */
    public void setUserRole(UserRole role) {
        this.role = role;
    }
    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }
    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * @param active the active
     */
    public boolean getActive() {
        return active;
    }
    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }
    /**
     * @return the lastLogin
     */
    public String getLastLogin() {
        return lastLogin;
    }
    /**
     * @param lastLogin the lastLogin to set
     */
    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }


    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return super.toString();
    }
}