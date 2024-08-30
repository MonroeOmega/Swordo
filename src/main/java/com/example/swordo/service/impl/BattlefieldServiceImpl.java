package com.example.swordo.service.impl;

import com.example.swordo.exceptions.BattlefieldNotFoundException;
import com.example.swordo.models.entity.Battlefield;
import com.example.swordo.models.entity.BattlefieldMonster;
import com.example.swordo.models.entity.BattlefieldSizeEnum;
import com.example.swordo.models.entity.MonsterClassEnum;
import com.example.swordo.repository.BattlefieldRepository;
import com.example.swordo.service.BattlefieldService;
import com.example.swordo.views.BattlefieldView;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class BattlefieldServiceImpl implements BattlefieldService {

    private final BattlefieldRepository battlefieldRepository;
    private final ModelMapper modelMapper;

    public BattlefieldServiceImpl(BattlefieldRepository battlefieldRepository, ModelMapper modelMapper) {
        this.battlefieldRepository = battlefieldRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void initBattlefield() {
        if (battlefieldRepository.count() != 0) {
            return;
        }
        Arrays.stream(BattlefieldSizeEnum.values())
                .forEach(bat -> {
                    Battlefield battlefield = new Battlefield();
                    battlefield.setSize(bat);
                    switch (bat) {
                        case SMALL -> {
                            battlefield.setDescription("A small field filled with snake and boar-like creatures");
                            battlefieldRepository.save(battlefield);
                        }
                        case MEDIUM -> {
                            battlefield.setDescription("A barren field filled to the brim with boar and humanoid monsters");
                            battlefieldRepository.save(battlefield);
                        }
                        case BIG -> {
                            battlefield.setDescription("A war was fought here. Only mutated humans and" +
                                    " gruesome bears can be fought here.");
                            battlefieldRepository.save(battlefield);
                        }
                    }
                });

    }

    @Override
    public Battlefield getBattlefield(BattlefieldSizeEnum size) {
        return battlefieldRepository.findFirstBySize(size);
    }

    @Override
    public List<Battlefield> getAllBattlefields() {
        return battlefieldRepository.findAll();
    }

    @Override
    public List<BattlefieldView> viewAllBattlefields() {
        return getAllBattlefields()
                .stream()
                .map(battlefield -> {
                    BattlefieldView battlefieldView = modelMapper.map(battlefield, BattlefieldView.class);
                    battlefieldView.setMonsterCount(battlefield
                            .getBattlefieldMonsters()
                            .stream().filter(monster -> !monster.isEngaged())
                            .toList().size());
                    return battlefieldView;
                })
                .collect(Collectors.toList());
    }

    @Override
    public BattlefieldView viewBattlefield(Long id) {
        return modelMapper.map(getBattlefieldById(id), BattlefieldView.class);
    }

    @Override
    public Battlefield getBattlefieldById(Long id) {
        return battlefieldRepository.findById(id)
                .orElseThrow(BattlefieldNotFoundException::new);
    }

}
