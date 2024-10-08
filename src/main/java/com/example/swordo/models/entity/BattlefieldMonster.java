package com.example.swordo.models.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "battlefield_monsters")
public class BattlefieldMonster extends BaseEntity{
    private Integer currentHitpoints;
    private Monster monster;
    private boolean engaged;
    private Battlefield battlefield;

    public BattlefieldMonster() {
    }

    @Column(nullable = false)
    public Integer getCurrentHitpoints() {
        return currentHitpoints;
    }

    public void setCurrentHitpoints(Integer currentHitpoints) {
        this.currentHitpoints = currentHitpoints;
    }

    @ManyToOne(fetch = FetchType.EAGER)
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

    @Column(nullable = false)
    public boolean isEngaged() {
        return engaged;
    }

    public void setEngaged(boolean engaged) {
        this.engaged = engaged;
    }
}
