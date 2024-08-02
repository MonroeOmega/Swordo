package com.example.swordo.current;

import com.example.swordo.models.entity.Battlefield;
import com.example.swordo.models.entity.Monster;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class CurrentBattlefieldMonster {
    private Long id;
    private Integer curentHitpoints;
    private Monster monster;
    private Long battlefieldId;

    public CurrentBattlefieldMonster() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCurentHitpoints() {
        return curentHitpoints;
    }

    public void setCurentHitpoints(Integer curentHitpoints) {
        this.curentHitpoints = curentHitpoints;
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
