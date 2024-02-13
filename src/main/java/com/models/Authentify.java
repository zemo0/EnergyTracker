package com.models;

public interface Authentify {
    String getUsername();
    String getPassword();
    void setUsername(String username);
    void setPassword(String password);
    String getRole();
    void setRole(String role);
}
