package com.example.swordo.models.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "swords_in_making")
public class SwordInMaking extends BaseEntity{
    private SwordTypeEnum type;
    private Integer maxStrength;
    private Integer minStrength;
    private Integer maxDurability;
    private Integer minDurability;
    private Integer maxCritChance;
    private Integer minCritChance;

    public SwordInMaking() {
    }

    @Enumerated(value = EnumType.STRING)
    public SwordTypeEnum getType() {
        return type;
    }

    public void setType(SwordTypeEnum type) {
        this.type = type;
    }

    @Column(nullable = false)
    public Integer getMaxStrength() {
        return maxStrength;
    }

    public void setMaxStrength(Integer maxStrength) {
        this.maxStrength = maxStrength;
    }

    @Column(nullable = false)
    public Integer getMinStrength() {
        return minStrength;
    }

    public void setMinStrength(Integer minStrength) {
        this.minStrength = minStrength;
    }

    @Column(nullable = false)
    public Integer getMaxDurability() {
        return maxDurability;
    }

    public void setMaxDurability(Integer maxDurability) {
        this.maxDurability = maxDurability;
    }

    @Column(nullable = false)
    public Integer getMinDurability() {
        return minDurability;
    }

    public void setMinDurability(Integer minDurability) {
        this.minDurability = minDurability;
    }

    @Column(nullable = false)
    public Integer getMaxCritChance() {
        return maxCritChance;
    }

    public void setMaxCritChance(Integer maxCritChance) {
        this.maxCritChance = maxCritChance;
    }

    @Column(nullable = false)
    public Integer getMinCritChance() {
        return minCritChance;
    }

    public void setMinCritChance(Integer minCritChance) {
        this.minCritChance = minCritChance;
    }
}
