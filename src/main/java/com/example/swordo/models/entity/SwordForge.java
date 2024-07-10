package com.example.swordo.models.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "forge")
public class SwordForge extends BaseEntity{
    private Sword sword;
    private Integer price;
    private SwordInMaking swordInMaking;

    public SwordForge() {
    }

    @OneToOne
    public Sword getSword() {
        return sword;
    }

    public void setSword(Sword sword) {
        this.sword = sword;
    }

    @Column(nullable = false)
    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @ManyToOne
    public SwordInMaking getSwordInMaking() {
        return swordInMaking;
    }

    public void setSwordInMaking(SwordInMaking swordInMaking) {
        this.swordInMaking = swordInMaking;
    }
}
