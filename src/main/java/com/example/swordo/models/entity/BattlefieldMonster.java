package com.example.swordo.models.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "battlefield_monsters")
public class BattlefieldMonster extends BaseEntity{
    private Integer curentHitpoints;
    private Monster monster;
    private Battlefield battlefield;

    public BattlefieldMonster() {
    }

    @Column(nullable = false)
    public Integer getCurentHitpoints() {
        return curentHitpoints;
    }

    public void setCurentHitpoints(Integer curentHitpoints) {
        this.curentHitpoints = curentHitpoints;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    public Monster getMonster() {
        return monster;
    }

    public void setMonster(Monster monster) {
        this.monster = monster;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    public Battlefield getBattlefield() {
        return battlefield;
    }

    public void setBattlefield(Battlefield battlefield) {
        this.battlefield = battlefield;
    }
}
