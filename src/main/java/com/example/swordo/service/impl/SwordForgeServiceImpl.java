package com.example.swordo.service.impl;

import com.example.swordo.models.entity.Sword;
import com.example.swordo.models.entity.SwordForge;
import com.example.swordo.models.entity.SwordInMaking;
import com.example.swordo.models.entity.SwordTypeEnum;
import com.example.swordo.repository.SwordForgeRepository;
import com.example.swordo.service.SwordForgeService;
import com.example.swordo.service.SwordInMakingService;
import com.example.swordo.service.SwordService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

@Service
public class SwordForgeServiceImpl implements SwordForgeService {
    private final SwordForgeRepository swordForgeRepository;
    private final SwordInMakingService swordInMakingService;
    private final SwordService swordService;

    public SwordForgeServiceImpl(SwordForgeRepository swordForgeRepository, SwordInMakingService swordInMakingService, SwordService swordService) {
        this.swordForgeRepository = swordForgeRepository;
        this.swordInMakingService = swordInMakingService;
        this.swordService = swordService;
    }

    @Override
    public void forgeSword(SwordTypeEnum type) {
        SwordInMaking swor = swordInMakingService.getSwordInMakingByType(type);
        Sword sword = new Sword();
        sword.setStrength(random(swor.getMinStrength(),swor.getMaxStrength()));
        sword.setDurability(random(swor.getMinDurability(), swor.getMaxDurability()));
        sword.setCritChance(random(swor.getMinCritChance(),swor.getMaxCritChance()));
        sword.setType(type);
        swordService.saveSword(sword);
        SwordForge swordForge = new SwordForge();
        swordForge.setSwordInMaking(swor);
        swordForge.setSword(sword);
        swordForge.setPrice(price());
        swordForgeRepository.save(swordForge);
    }

    @Override
    public int random(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    @Override
    public void initSwords() {
        if(swordForgeRepository.count() != 0){
            return;
        }
        addSwordSets(1);
    }

    @Override
    public void addSwordSets(int sets) {
        if(sets < 0){
            return;
        }
        IntStream.range(0, sets)
                .forEach( check -> Arrays.stream(SwordTypeEnum.values())
                        .forEach(swordTypeEnum -> {
                            if (swordTypeEnum != SwordTypeEnum.BROKEN_SWORD){
                                forgeSword(swordTypeEnum);
                            }
                        }));
    }



    @Override
    public int price() {
        //Make later
        return 100;
    }




}
