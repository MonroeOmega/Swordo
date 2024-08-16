package com.example.swordo.views;

import com.example.swordo.models.entity.Monster;

public class MonsterView {
    private Long id;
    private Integer currentHitpoints;
    private Monster monster;
    private Long battlefieldId;

    public MonsterView() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCurrentHitpoints() {
        return currentHitpoints;
    }

    public void setCurrentHitpoints(Integer currentHitpoints) {
        this.currentHitpoints = currentHitpoints;
    }

    public Monster getMonster() {
        return monster;
    }

    public void setMonster(Monster monster) {
        this.monster = monster;
    }

    public Long getBattlefieldId() {
        return battlefieldId;
    }

    public void setBattlefieldId(Long battlefieldId) {
        this.battlefieldId = battlefieldId;
    }
}
