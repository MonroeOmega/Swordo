package com.example.swordo.views;

import com.example.swordo.models.entity.BattlefieldSizeEnum;

public class BattlefieldView {
    private Long id;
    private BattlefieldSizeEnum size;
    private int monsterCount;
    private String description;

    public BattlefieldView() {
    }

    public BattlefieldSizeEnum getSize() {
        return size;
    }

    public void setSize(BattlefieldSizeEnum size) {
        this.size = size;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMonsterCount() {
        return monsterCount;
    }

    public void setMonsterCount(int monsterCount) {
        this.monsterCount = monsterCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
