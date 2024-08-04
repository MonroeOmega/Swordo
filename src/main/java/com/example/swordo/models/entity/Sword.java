package com.example.swordo.models.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "swords")
public class Sword extends BaseEntity{
    private SwordTypeEnum type;
    private Integer strength;
    private Integer durability;
    private Integer critChance;

    public Sword() {
    }

    @Enumerated(EnumType.STRING)
    public SwordTypeEnum getType() {
        return type;
    }

    public void setType(SwordTypeEnum type) {
        this.type = type;
    }

    @Column(nullable = false)
    public Integer getStrength() {
        return strength;
    }

    public void setStrength(Integer strength) {
        this.strength = strength;
    }

    @Column(nullable = false)
    public Integer getDurability() {
        return durability;
    }

    public void setDurability(Integer durability) {
        this.durability = durability;
    }

    @Column(nullable = false)
    public Integer getCritChance() {
        return critChance;
    }

    public void setCritChance(Integer critChance) {
        this.critChance = critChance;
    }
}
