package com.example.swordo.service.impl;

import com.example.swordo.models.entity.Monster;
import com.example.swordo.models.entity.MonsterClassEnum;
import com.example.swordo.models.entity.SwordTypeEnum;
import com.example.swordo.repository.MonsterRepository;
import com.example.swordo.service.MonsterService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class MonsterServiceImpl implements MonsterService {

    private final MonsterRepository monsterRepository;

    public MonsterServiceImpl(MonsterRepository monsterRepository) {
        this.monsterRepository = monsterRepository;
    }

    @Override
    public void initMonsters() {
        if (monsterRepository.count() != 0){
            return;
        }

        Arrays.stream(MonsterClassEnum.values())
                .forEach(mon ->{
                    Monster monster = new Monster();
                    monster.setClasss(mon);
                    switch (mon){
                        case SNAKER -> {
                            monster.setMaxHitpoints(200);
                            monster.setWeakness(SwordTypeEnum.DAGGER);
                            monster.setMaxStrike(10);
                            monster.setMinStrike(8);
                            monster.setMinCoins(50);
                            monster.setMaxCoins(75);
                            monsterRepository.save(monster);
                        }
                        case BOARER -> {
                            monster.setMaxHitpoints(500);
                            monster.setWeakness(SwordTypeEnum.SHORTSWORD);
                            monster.setMaxStrike(15);
                            monster.setMinStrike(12);
                            monster.setMinCoins(100);
                            monster.setMaxCoins(125);
                            monsterRepository.save(monster);
                        }
                        case HUMANLIKER -> {
                            monster.setMaxHitpoints(1000);
                            monster.setWeakness(SwordTypeEnum.LONGSWORD);
                            monster.setMaxStrike(23);
                            monster.setMinStrike(18);
                            monster.setMinCoins(125);
                            monster.setMaxCoins(150);
                            monsterRepository.save(monster);
                        }
                        case BEARER -> {
                            monster.setMaxHitpoints(2000);
                            monster.setWeakness(SwordTypeEnum.GREATSWORD);
                            monster.setMaxStrike(40);
                            monster.setMinStrike(30);
                            monster.setMinCoins(150);
                            monster.setMaxCoins(200);
                            monsterRepository.save(monster);
                        }
                        case JIMMY_OMEGA -> {
                            monster.setMaxHitpoints(Integer.MAX_VALUE);
                            monster.setMaxStrike(Integer.MAX_VALUE);
                            monster.setMinStrike(Integer.MIN_VALUE);
                            monster.setMinCoins(Integer.MIN_VALUE);
                            monster.setMaxCoins(Integer.MAX_VALUE);
                            monsterRepository.save(monster);
                        }
                    }
                });

    }

    @Override
    public Monster getMonsters(MonsterClassEnum classs) {
        return monsterRepository.findFirstByClasss(classs);
    }


}
