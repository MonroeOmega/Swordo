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
                            monster.setMaxHitpoints(100);
                            monster.setWeakness(SwordTypeEnum.DAGGER);
                            monster.setMaxStrike(10);
                            monster.setMinStrike(8);
                            monsterRepository.save(monster);
                        }
                        case BOARER -> {
                            monster.setMaxHitpoints(150);
                            monster.setWeakness(SwordTypeEnum.SHORTSWORD);
                            monster.setMaxStrike(15);
                            monster.setMinStrike(12);
                            monsterRepository.save(monster);
                        }
                        case HUMANLIKER -> {
                            monster.setMaxHitpoints(200);
                            monster.setWeakness(SwordTypeEnum.LONGSWORD);
                            monster.setMaxStrike(23);
                            monster.setMinStrike(18);
                            monsterRepository.save(monster);
                        }
                        case BEARER -> {
                            monster.setMaxHitpoints(300);
                            monster.setWeakness(SwordTypeEnum.GREATSWORD);
                            monster.setMaxStrike(40);
                            monster.setMinStrike(30);
                            monsterRepository.save(monster);
                        }
                        case JIMMY_OMEGA -> {
                            monster.setMaxHitpoints(Integer.MAX_VALUE);
                            monster.setMaxStrike(Integer.MAX_VALUE);
                            monster.setMinStrike(Integer.MIN_VALUE);
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
