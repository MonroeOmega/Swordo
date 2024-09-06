package com.example.swordo.service.impl;

import com.example.swordo.current.ExtraUserData;
import com.example.swordo.models.entity.Sword;
import com.example.swordo.models.entity.SwordTypeEnum;
import com.example.swordo.repository.SwordRepository;
import com.example.swordo.service.SwordService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SwordServiceImpl implements SwordService {

    private final SwordRepository swordRepository;

    public SwordServiceImpl(SwordRepository swordRepository, ExtraUserData extraUserData) {
        this.swordRepository = swordRepository;
    }

    @Override
    public void saveSword(Sword sword) {
        swordRepository.save(sword);
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

    @Override
    public long swordCount() {
        return swordRepository.count();
    }

    @Override
    public void adminDelete(List<Long> ids) {
        swordRepository.deleteAllById(ids);
    }

    @Override
    public List<Sword> getAll() {
        return swordRepository.findAll();
    }
}
