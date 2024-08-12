package com.example.swordo.models.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "battlefields")
public class Battlefield extends BaseEntity{
    private BattlefieldSizeEnum size;
    private List<BattlefieldMonster> battlefieldMonsters;
    private String description;

    public Battlefield() {
    }

    @Enumerated(value = EnumType.STRING)
    public BattlefieldSizeEnum getSize() {
        return size;
    }

    public void setSize(BattlefieldSizeEnum size) {
        this.size = size;
    }

    @OneToMany(mappedBy = "battlefield",fetch = FetchType.EAGER)
    public List<BattlefieldMonster> getBattlefieldMonsters() {
        return battlefieldMonsters;
    }

    public void setBattlefieldMonsters(List<BattlefieldMonster> battlefieldMonsters) {
        this.battlefieldMonsters = battlefieldMonsters;
    }

    @Column
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
