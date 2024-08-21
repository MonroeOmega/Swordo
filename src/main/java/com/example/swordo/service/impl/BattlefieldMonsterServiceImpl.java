package com.example.swordo.service.impl;

import com.example.swordo.current.CurrentBattlefieldMonster;
import com.example.swordo.exceptions.CheekyException;
import com.example.swordo.exceptions.MonsterAlreadyEngagedException;
import com.example.swordo.exceptions.MonsterMissingException;
import com.example.swordo.models.entity.BattlefieldMonster;
import com.example.swordo.models.entity.BattlefieldSizeEnum;
import com.example.swordo.models.entity.Monster;
import com.example.swordo.models.entity.MonsterClassEnum;
import com.example.swordo.repository.BattlefieldMonsterRepository;
import com.example.swordo.service.BattlefieldMonsterService;
import com.example.swordo.service.BattlefieldService;
import com.example.swordo.service.MonsterService;
import com.example.swordo.views.MonsterView;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class BattlefieldMonsterServiceImpl implements BattlefieldMonsterService {

    private final MonsterService monsterService;
    private final BattlefieldService battlefieldService;
    private final BattlefieldMonsterRepository battlefieldMonsterRepository;
    private final CurrentBattlefieldMonster currentBattlefieldMonster;
    private final ModelMapper modelMapper;

    public BattlefieldMonsterServiceImpl(MonsterService monsterService, BattlefieldService battlefieldService, BattlefieldMonsterRepository battlefieldMonsterRepository, CurrentBattlefieldMonster currentBattlefieldMonster, ModelMapper modelMapper) {
        this.monsterService = monsterService;
        this.battlefieldService = battlefieldService;
        this.battlefieldMonsterRepository = battlefieldMonsterRepository;
        this.currentBattlefieldMonster = currentBattlefieldMonster;
        this.modelMapper = modelMapper;
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
            battlefieldMonster.setEngaged(false);
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
        BattlefieldMonster battlefieldMonster = battlefieldMonsterRepository.findById(id).orElseThrow(MonsterMissingException::new);
        if(battlefieldMonster.isEngaged()){
            throw new MonsterAlreadyEngagedException();
        }
        battlefieldMonster.setEngaged(true);
        battlefieldMonsterRepository.save(battlefieldMonster);
        currentBattlefieldMonster.setId(battlefieldMonster.getId());
        currentBattlefieldMonster.setBattlefieldId(battlefieldMonster.getBattlefield().getId());
        currentBattlefieldMonster.setMonster(battlefieldMonster.getMonster());
        currentBattlefieldMonster.setCurrentHitpoints(battlefieldMonster.getCurrentHitpoints());
        if(battlefieldMonster.getMonster().getClasss() != MonsterClassEnum.JIMMY_OMEGA) {;
            currentBattlefieldMonster.setLoot(
                    random(battlefieldMonster.getMonster().getMinCoins()
                            , battlefieldMonster.getMonster().getMaxCoins()));
        }else{
            currentBattlefieldMonster.setLoot(999999);
        }
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
            int hitpoints = currentBattlefieldMonster.getCurrentHitpoints() + ((currentBattlefieldMonster.getMonster().getMaxHitpoints() - currentBattlefieldMonster.getCurrentHitpoints())/2);
            monster.setCurrentHitpoints(hitpoints);
            monster.setEngaged(false);
            battlefieldMonsterRepository.save(monster);
            currentBattlefieldMonster.setId(null);
        }
    }

    @Override
    public void adminAddMonsters(BattlefieldSizeEnum battlefieldSizeEnum) {
        switch (battlefieldSizeEnum){
            case BIG -> populateBigBattlefield();
            case MEDIUM -> populateMediumBattlefield();
            case SMALL -> populateSmallBattlefield();
        }
    }

    @Override
    public List<MonsterView> viewMonsters(Long id) {
        return battlefieldService
                .getBattlefieldById(id)
                .getBattlefieldMonsters()
                .stream()
                .filter(monster -> !monster.isEngaged())
                .map(monster -> modelMapper.map(monster, MonsterView.class))
                .collect(Collectors.toList());
    }

    @Override
    public void addJimmyOmega(BattlefieldSizeEnum battlefieldSizeEnum) {
        battlefieldMonsterRepository.deleteBattlefieldMonsterByMonster_Id(5L);
        Monster monster = monsterService.getMonsters(MonsterClassEnum.JIMMY_OMEGA);
        BattlefieldMonster battlefieldMonster = new BattlefieldMonster();
        battlefieldMonster.setMonster(monster);
        battlefieldMonster.setCurrentHitpoints(monster.getMaxHitpoints());
        battlefieldMonster.setBattlefield(battlefieldService.getBattlefield(battlefieldSizeEnum));
        battlefieldMonsterRepository.save(battlefieldMonster);
    }

    @Override
    public boolean checkForHim() {
        return currentBattlefieldMonster.getMonster().getClasss() == MonsterClassEnum.JIMMY_OMEGA;
    }

    @Override
    public long checkForHisId() {
        BattlefieldMonster battlefieldMonster = battlefieldMonsterRepository.findBattlefieldMonsterByMonster_Id(5L);
        if(battlefieldMonster != null){
            return battlefieldMonster.getId();
        }
        return 0L;
    }

    @Override
    public void giveHimSomeCheese() {
        battlefieldMonsterRepository.deleteById(checkForHisId());
    }

    @Override
    public boolean checkBattlefields() {
        return battlefieldMonsterRepository.findBattlefieldMonsterByMonster_Id(5L) != null;
    }

    @Override
    public void checkForCheekines() {
        if(currentBattlefieldMonster.getId() != null){
            throw new CheekyException();
        }
    }


}
