package com.example.swordo.models.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User extends BaseEntity{
    private String username;
    private String password;
    private String email;
    private UserRoleEnum role;
    private int hitpoints;
    private int coins;
    private Sword sword;

    public User() {
    }

    @Column(nullable = false)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Enumerated(EnumType.STRING)
    public UserRoleEnum getRole() {
        return role;
    }

    public void setRole(UserRoleEnum role) {
        this.role = role;
    }

    @Column(nullable = false)
    public int getHitpoints() {
        return hitpoints;
    }

    public void setHitpoints(int hitpoints) {
        this.hitpoints = hitpoints;
    }

    @Column(nullable = false)
    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    @OneToOne
    public Sword getSword() {
        return sword;
    }

    public void setSword(Sword sword) {
        this.sword = sword;
    }
}
