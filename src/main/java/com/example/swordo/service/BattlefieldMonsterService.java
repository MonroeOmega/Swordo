package com.example.swordo.service;

import com.example.swordo.models.entity.BattlefieldMonster;
import com.example.swordo.models.entity.MonsterClassEnum;

import java.util.List;

public interface BattlefieldMonsterService {
    List<BattlefieldMonster> getNewBattlefieldMonsters(int i, MonsterClassEnum classs);
    void populateSmallBattlefield();
    void populateMediumBattlefield();
    void populateBigBattlefield();
    void loadFirstMonsters();
    void loadCurrentBattlefieldMonsterData(Long id);
    void disposeOfCurrentMonster();
    void returnCurrentMonster();
}
