package com.example.swordo.service.impl;

import com.example.swordo.models.entity.BattlefieldMonster;
import com.example.swordo.models.entity.BattlefieldSizeEnum;
import com.example.swordo.models.entity.MonsterClassEnum;
import com.example.swordo.repository.BattlefieldMonsterRepository;
import com.example.swordo.service.BattlefieldMonsterService;
import com.example.swordo.service.BattlefieldService;
import com.example.swordo.service.MonsterService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class BattlefieldMonsterServiceImpl implements BattlefieldMonsterService {

    private final MonsterService monsterService;
    private final BattlefieldService battlefieldService;
    private final BattlefieldMonsterRepository battlefieldMonsterRepository;

    public BattlefieldMonsterServiceImpl(MonsterService monsterService, BattlefieldService battlefieldService, BattlefieldMonsterRepository battlefieldMonsterRepository) {
        this.monsterService = monsterService;
        this.battlefieldService = battlefieldService;
        this.battlefieldMonsterRepository = battlefieldMonsterRepository;
    }

    @Override
    public List<BattlefieldMonster> getBattlefieldMonsters(int i, MonsterClassEnum classs) {
        List<BattlefieldMonster> battlefieldMonsters = new ArrayList<>();
        for (int z = 0; z < i; z++){
            BattlefieldMonster battlefieldMonster = new BattlefieldMonster();
            battlefieldMonster.setMonster(monsterService.getMonsters(classs));
            battlefieldMonster.setCurentHitpoints(battlefieldMonster.getMonster().getMaxHitpoints());
            battlefieldMonsters.add(battlefieldMonster);
        }
        return battlefieldMonsters;
    }

    @Override
    public void populateSmallBattlefield() {
        List<BattlefieldMonster> battlefieldMonsters = new ArrayList<>();
        battlefieldMonsters.addAll(getBattlefieldMonsters(4, MonsterClassEnum.SNAKER));
        battlefieldMonsters.addAll(getBattlefieldMonsters(2, MonsterClassEnum.BOARER));
        battlefieldMonsters.forEach(batmon -> batmon.setBattlefield(battlefieldService
                .getBattlefield(BattlefieldSizeEnum.SMALL)));
        battlefieldMonsterRepository.saveAll(battlefieldMonsters);
    }

    @Override
    public void populateMediumBattlefield() {
        List<BattlefieldMonster> battlefieldMonsters = new ArrayList<>();
        battlefieldMonsters.addAll(getBattlefieldMonsters(4, MonsterClassEnum.BOARER));
        battlefieldMonsters.addAll(getBattlefieldMonsters(2, MonsterClassEnum.HUMANLIKER));
        battlefieldMonsters.forEach(batmon -> batmon.setBattlefield(battlefieldService
                .getBattlefield(BattlefieldSizeEnum.MEDIUM)));
        battlefieldMonsterRepository.saveAll(battlefieldMonsters);
    }

    @Override
    public void populateBigBattlefield() {
        List<BattlefieldMonster> battlefieldMonsters = new ArrayList<>();
        battlefieldMonsters.addAll(getBattlefieldMonsters(4, MonsterClassEnum.HUMANLIKER));
        battlefieldMonsters.addAll(getBattlefieldMonsters(2, MonsterClassEnum.BEARER));
        battlefieldMonsters.forEach(batmon -> batmon.setBattlefield(battlefieldService
                .getBattlefield(BattlefieldSizeEnum.BIG)));
        battlefieldMonsterRepository.saveAll(battlefieldMonsters);
    }
    @Override
    public void loadFirstMonsters() {
        if (battlefieldMonsterRepository.count() != 0){
            return;
        }
        populateSmallBattlefield();
        populateMediumBattlefield();
        populateBigBattlefield();
    }


}
