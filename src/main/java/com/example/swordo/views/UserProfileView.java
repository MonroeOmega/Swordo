package com.example.swordo.views;

import com.example.swordo.models.entity.Sword;
import com.example.swordo.models.entity.SwordTypeEnum;
import com.example.swordo.models.entity.UserRoleEnum;

public class UserProfileView {
    private String username;
    private String email;
    private int hitpoints;
    private int coins;
    private UserRoleEnum role;
    private Sword sword;

    public UserProfileView() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public UserRoleEnum getRole() {
        return role;
    }

    public void setRole(UserRoleEnum role) {
        this.role = role;
    }

    public Sword getSword() {
        return sword;
    }

    public void setSword(Sword sword) {
        this.sword = sword;
    }
}
