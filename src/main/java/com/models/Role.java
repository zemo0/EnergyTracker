package com.models;

import java.io.Serializable;
import java.util.Objects;

public abstract class Role implements Serializable, RoleMethods {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role1 = (Role) o;
        return Objects.equals(username, role1.username) && Objects.equals(password, role1.password) && Objects.equals(role, role1.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, role);
    }
}
