package com.example.swordo.service.impl;

import com.example.swordo.current.ExtraUserData;
import com.example.swordo.models.entity.Sword;
import com.example.swordo.models.entity.SwordTypeEnum;
import com.example.swordo.repository.SwordRepository;
import com.example.swordo.service.SwordService;
import org.springframework.stereotype.Service;

@Service
public class SwordServiceImpl implements SwordService {

    private final SwordRepository swordRepository;
    private final ExtraUserData extraUserData;

    public SwordServiceImpl(SwordRepository swordRepository, ExtraUserData extraUserData) {
        this.swordRepository = swordRepository;
        this.extraUserData = extraUserData;
    }

    @Override
    public void saveSword(Sword sword) {
        //Testing something. Change later if there is a problem.
        swordRepository.save(sword);
    }

    @Override
    public Sword getSword(Long id) {
        return swordRepository.findFirstById(id);
    }

    @Override
    public Sword getBroken() {
        Sword sword = new Sword();
        sword.setType(SwordTypeEnum.BROKEN_SWORD);
        sword.setDurability(9999);
        sword.setCritChance(1);
        sword.setStrength(10);
        return sword;
    }


    @Override
    public void discard(Long id) {
        swordRepository.deleteById(id);
    }
}
