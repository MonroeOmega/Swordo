package com.example.swordo.service.impl;

import com.example.swordo.current.CurrentBattlefieldMonster;
import com.example.swordo.models.entity.BattlefieldMonster;
import com.example.swordo.models.entity.BattlefieldSizeEnum;
import com.example.swordo.models.entity.MonsterClassEnum;
import com.example.swordo.repository.BattlefieldMonsterRepository;
import com.example.swordo.service.BattlefieldMonsterService;
import com.example.swordo.service.BattlefieldService;
import com.example.swordo.service.MonsterService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class BattlefieldMonsterServiceImpl implements BattlefieldMonsterService {

    private final MonsterService monsterService;
    private final BattlefieldService battlefieldService;
    private final BattlefieldMonsterRepository battlefieldMonsterRepository;
    private final CurrentBattlefieldMonster currentBattlefieldMonster;

    public BattlefieldMonsterServiceImpl(MonsterService monsterService, BattlefieldService battlefieldService, BattlefieldMonsterRepository battlefieldMonsterRepository, CurrentBattlefieldMonster currentBattlefieldMonster) {
        this.monsterService = monsterService;
        this.battlefieldService = battlefieldService;
        this.battlefieldMonsterRepository = battlefieldMonsterRepository;
        this.currentBattlefieldMonster = currentBattlefieldMonster;
    }

    public int random(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    @Override
    public List<BattlefieldMonster> getNewBattlefieldMonsters(int i, MonsterClassEnum classs) {
        List<BattlefieldMonster> battlefieldMonsters = new ArrayList<>();
        for (int z = 0; z < i; z++) {
            BattlefieldMonster battlefieldMonster = new BattlefieldMonster();
            battlefieldMonster.setMonster(monsterService.getMonsters(classs));
            battlefieldMonster.setCurrentHitpoints(battlefieldMonster.getMonster().getMaxHitpoints());
            battlefieldMonsters.add(battlefieldMonster);
        }
        return battlefieldMonsters;
    }

    @Override
    public void populateSmallBattlefield() {
        List<BattlefieldMonster> battlefieldMonsters = new ArrayList<>();
        battlefieldMonsters.addAll(getNewBattlefieldMonsters(4, MonsterClassEnum.SNAKER));
        battlefieldMonsters.addAll(getNewBattlefieldMonsters(2, MonsterClassEnum.BOARER));
        battlefieldMonsters.forEach(batmon -> batmon.setBattlefield(battlefieldService
                .getBattlefield(BattlefieldSizeEnum.SMALL)));
        battlefieldMonsterRepository.saveAll(battlefieldMonsters);
    }

    @Override
    public void populateMediumBattlefield() {
        List<BattlefieldMonster> battlefieldMonsters = new ArrayList<>();
        battlefieldMonsters.addAll(getNewBattlefieldMonsters(4, MonsterClassEnum.BOARER));
        battlefieldMonsters.addAll(getNewBattlefieldMonsters(2, MonsterClassEnum.HUMANLIKER));
        battlefieldMonsters.forEach(batmon -> batmon.setBattlefield(battlefieldService
                .getBattlefield(BattlefieldSizeEnum.MEDIUM)));
        battlefieldMonsterRepository.saveAll(battlefieldMonsters);
    }

    @Override
    public void populateBigBattlefield() {
        List<BattlefieldMonster> battlefieldMonsters = new ArrayList<>();
        battlefieldMonsters.addAll(getNewBattlefieldMonsters(4, MonsterClassEnum.HUMANLIKER));
        battlefieldMonsters.addAll(getNewBattlefieldMonsters(2, MonsterClassEnum.BEARER));
        battlefieldMonsters.forEach(batmon -> batmon.setBattlefield(battlefieldService
                .getBattlefield(BattlefieldSizeEnum.BIG)));
        battlefieldMonsterRepository.saveAll(battlefieldMonsters);
    }

    @Override
    public void loadFirstMonsters() {
        if (battlefieldMonsterRepository.count() != 0) {
            return;
        }
        populateSmallBattlefield();
        populateMediumBattlefield();
        populateBigBattlefield();
    }

    @Override
    public void loadCurrentBattlefieldMonsterData(Long id) {
        BattlefieldMonster battlefieldMonster = battlefieldMonsterRepository.findById(id).orElse(null);
        currentBattlefieldMonster.setId(battlefieldMonster.getId());
        currentBattlefieldMonster.setBattlefieldId(battlefieldMonster.getBattlefield().getId());
        currentBattlefieldMonster.setCurrentHitpoints(battlefieldMonster.getCurrentHitpoints());
        currentBattlefieldMonster.setMonster(battlefieldMonster.getMonster());
        currentBattlefieldMonster.setLoot(
                random(battlefieldMonster.getMonster().getMinCoins()
                        , battlefieldMonster.getMonster().getMaxCoins()));
    }

    @Override
    public void disposeOfCurrentMonster() {
        Long removalId = currentBattlefieldMonster.getId();
        battlefieldMonsterRepository.deleteById(removalId);
        currentBattlefieldMonster.setId(null);
    }

    @Override
    public void returnCurrentMonster() {
        if (currentBattlefieldMonster.getId() != null) {
            BattlefieldMonster monster = battlefieldMonsterRepository.findById(currentBattlefieldMonster.getId()).orElse(null);
            monster.setCurrentHitpoints(currentBattlefieldMonster.getCurrentHitpoints());
            battlefieldMonsterRepository.save(monster);
            currentBattlefieldMonster.setId(null);
        }
    }


}
