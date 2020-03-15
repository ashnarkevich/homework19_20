package com.gmail.petrikov05.app.repository.model;

import com.gmail.petrikov05.app.repository.constant.RoleEnum;

public class User {

    private String username;
    private String password;
    private RoleEnum role;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

}
