package com.models;

import java.io.Serializable;

public abstract class Role implements Serializable, Authentify {
    private String username;
    private String password;
    private String role;
    public Role(String username, String password, String role){
        this.username = username;
        this.password = password;
        this.role = role;
    }
    @Override
    public String getUsername() {
        return username;
    }
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getRole() {
        return role;
    }
    @Override
    public void setUsername(String username) {
        this.username = username;
    }
    @Override
    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public void setRole(String role) {
        this.role = role;
    }
    @Override
    public String toString() {
        return "Username='" + username + '\''+
                ", role='" + role + '\'';
    }
}
