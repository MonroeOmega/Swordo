package com.example.swordo.current;

import com.example.swordo.models.entity.Battlefield;
import com.example.swordo.models.entity.Monster;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class CurrentBattlefieldMonster {
    private Long id;
    private Integer currentHitpoints;
    private Monster monster;
    private Long battlefieldId;
    private Integer loot;

    public CurrentBattlefieldMonster() {
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

    public Integer getLoot() {
        return loot;
    }

    public void setLoot(Integer loot) {
        this.loot = loot;
    }
}
