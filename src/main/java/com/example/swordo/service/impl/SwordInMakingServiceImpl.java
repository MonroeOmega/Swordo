package com.example.swordo.service.impl;

import com.example.swordo.models.entity.SwordInMaking;
import com.example.swordo.models.entity.SwordTypeEnum;
import com.example.swordo.repository.SwordInMakingRepository;
import com.example.swordo.service.SwordInMakingService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class SwordInMakingServiceImpl implements SwordInMakingService {

    private final SwordInMakingRepository swordInMakingRepository;

    public SwordInMakingServiceImpl(SwordInMakingRepository swordInMakingRepository) {
        this.swordInMakingRepository = swordInMakingRepository;
    }

    @Override
    public void initSwordsInMaking() {
        if(swordInMakingRepository.count() != 0){
            return;
        }

        Arrays.stream(SwordTypeEnum.values())
                .forEach(swor ->{
                    SwordInMaking sword = new SwordInMaking();
                    sword.setType(swor);
                    switch (swor){
                        case DAGGER -> {
                            sword.setMinStrength(20);
                            sword.setMaxStrength(30);
                            sword.setMinDurability(30);
                            sword.setMaxDurability(50);
                            sword.setMinCritChance(10);
                            sword.setMaxCritChance(20);
                            swordInMakingRepository.save(sword);
                        }
                        case SHORTSWORD -> {
                            sword.setMinStrength(30);
                            sword.setMaxStrength(40);
                            sword.setMinDurability(30);
                            sword.setMaxDurability(40);
                            sword.setMinCritChance(7);
                            sword.setMaxCritChance(17);
                            swordInMakingRepository.save(sword);
                        }
                        case LONGSWORD -> {
                            sword.setMinStrength(40);
                            sword.setMaxStrength(50);
                            sword.setMinDurability(25);
                            sword.setMaxDurability(40);
                            sword.setMinCritChance(5);
                            sword.setMaxCritChance(15);
                            swordInMakingRepository.save(sword);
                        }
                        case GREATSWORD -> {
                            sword.setMinStrength(50);
                            sword.setMaxStrength(70);
                            sword.setMinDurability(20);
                            sword.setMaxDurability(30);
                            sword.setMinCritChance(2);
                            sword.setMaxCritChance(10);
                            swordInMakingRepository.save(sword);
                        }
                        case BROKEN_SWORD -> {
                            sword.setMinStrength(10);
                            sword.setMaxStrength(10);
                            sword.setMinDurability(9999);
                            sword.setMaxDurability(9999);
                            sword.setMinCritChance(1);
                            sword.setMaxCritChance(1);
                            swordInMakingRepository.save(sword);
                        }
                    }
                });

    }

    @Override
    public SwordInMaking getSwordInMakingByType(SwordTypeEnum type) {
        return swordInMakingRepository.getFirstByType(type);
    }


}
