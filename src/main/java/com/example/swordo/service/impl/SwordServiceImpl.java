package com.example.swordo.service.impl;

import com.example.swordo.models.entity.Sword;
import com.example.swordo.repository.SwordRepository;
import com.example.swordo.service.SwordService;
import org.springframework.stereotype.Service;

@Service
public class SwordServiceImpl implements SwordService {

    private final SwordRepository swordRepository;

    public SwordServiceImpl(SwordRepository swordRepository) {
        this.swordRepository = swordRepository;
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
}
