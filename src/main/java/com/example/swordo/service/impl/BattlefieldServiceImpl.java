package com.example.swordo.service.impl;

import com.example.swordo.models.entity.Battlefield;
import com.example.swordo.models.entity.BattlefieldSizeEnum;
import com.example.swordo.repository.BattlefieldRepository;
import com.example.swordo.service.BattlefieldMonsterService;
import com.example.swordo.service.BattlefieldService;
import org.springframework.stereotype.Service;

import java.util.Arrays;


@Service
public class BattlefieldServiceImpl implements BattlefieldService {

    private final BattlefieldRepository battlefieldRepository;

    public BattlefieldServiceImpl(BattlefieldRepository battlefieldRepository) {
        this.battlefieldRepository = battlefieldRepository;
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
                                    " gruesome bears can be fought here. Although... You fill like something " +
                                    " terrible lurks in the shadows of the barren land");
                            battlefieldRepository.save(battlefield);
                        }
                    }
                });

    }

    @Override
    public Battlefield getBattlefield(BattlefieldSizeEnum size) {
        return battlefieldRepository.findFirstBySize(size);
    }

}
