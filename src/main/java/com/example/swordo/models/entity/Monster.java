package com.example.swordo.models.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "monsters")
public class Monster extends BaseEntity{
    private MonsterClassEnum classs;
    private Integer maxHitpoints;
    private Integer maxStrike;
    private Integer minStrike;
    private SwordTypeEnum weakness;

    private List<BattlefieldMonster> battlefieldMonsters;

    public Monster() {
    }

    @Column(nullable = false)
    public MonsterClassEnum getClasss() {
        return classs;
    }

    public void setClasss(MonsterClassEnum classs) {
        this.classs = classs;
    }

    @Column(nullable = false)
    public Integer getMaxHitpoints() {
        return maxHitpoints;
    }

    public void setMaxHitpoints(Integer maxHitpoints) {
        this.maxHitpoints = maxHitpoints;
    }

    @Column(nullable = false)
    public Integer getMaxStrike() {
        return maxStrike;
    }

    public void setMaxStrike(Integer maxStrike) {
        this.maxStrike = maxStrike;
    }

    @Column(nullable = false)
    public Integer getMinStrike() {
        return minStrike;
    }

    public void setMinStrike(Integer minStrike) {
        this.minStrike = minStrike;
    }

    @Enumerated(EnumType.STRING)
    public SwordTypeEnum getWeakness() {
        return weakness;
    }

    public void setWeakness(SwordTypeEnum weakness) {
        this.weakness = weakness;
    }

    @OneToMany(mappedBy = "monster")
    public List<BattlefieldMonster> getBattlefieldMonsters() {
        return battlefieldMonsters;
    }

    public void setBattlefieldMonsters(List<BattlefieldMonster> battlefieldMonsters) {
        this.battlefieldMonsters = battlefieldMonsters;
    }
}
