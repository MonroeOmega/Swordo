package com.example.swordo.models.service;

import com.example.swordo.models.entity.Sword;
import com.example.swordo.models.entity.UserRoleEnum;

public class UserServiceModel {
    private Long id;
    private String username;
    private String password;
    private String email;
    private int hitpoints;
    private int coins;
    private Sword sword;
    private UserRoleEnum role;

    public UserServiceModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getHitpoints() {
        return hitpoints;
    }

    public void setHitpoints(int hitpoints) {
        this.hitpoints = hitpoints;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public Sword getSword() {
        return sword;
    }

    public void setSword(Sword sword) {
        this.sword = sword;
    }

    public UserRoleEnum getRole() {
        return role;
    }

    public void setRole(UserRoleEnum role) {
        this.role = role;
    }
}
