package com.example.swordo.service.impl;

import com.example.swordo.current.ExtraUserData;
import com.example.swordo.models.entity.Sword;
import com.example.swordo.models.entity.SwordForge;
import com.example.swordo.models.entity.SwordInMaking;
import com.example.swordo.models.entity.SwordTypeEnum;
import com.example.swordo.repository.SwordForgeRepository;
import com.example.swordo.service.SwordForgeService;
import com.example.swordo.service.SwordInMakingService;
import com.example.swordo.service.SwordService;
import com.example.swordo.service.UserService;
import com.example.swordo.views.SwordShopView;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class SwordForgeServiceImpl implements SwordForgeService {
    private final SwordForgeRepository swordForgeRepository;
    private final SwordInMakingService swordInMakingService;
    private final SwordService swordService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final ExtraUserData extraUserData;

    public SwordForgeServiceImpl(SwordForgeRepository swordForgeRepository, SwordInMakingService swordInMakingService, SwordService swordService, UserService userService, ModelMapper modelMapper, ExtraUserData extraUserData) {
        this.swordForgeRepository = swordForgeRepository;
        this.swordInMakingService = swordInMakingService;
        this.swordService = swordService;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.extraUserData = extraUserData;
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
        swordForge.setPrice(price(sword));
        swordForgeRepository.save(swordForge);
    }

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
    public int price(Sword sword) {
        int price = 100;
        SwordInMaking swordInMaking = swordInMakingService.getSwordInMakingByType(sword.getType());

        price+= 10 * (sword.getCritChance() - swordInMaking.getMinCritChance());
        price+= 5 * (sword.getStrength() - swordInMaking.getMinStrength());
        price+= 2 * (sword.getDurability() - swordInMaking.getMinDurability());

        double modifier = 0.1;
        switch (sword.getType()){
            case DAGGER -> modifier = 0.8;
            case SHORTSWORD -> modifier = 1.0;
            case LONGSWORD -> modifier = 1.2;
            case GREATSWORD -> modifier = 1.5;
        }
        price = (int) (price * modifier);

        return price;
    }

    @Override
    public List<SwordShopView> shopView() {
        return swordForgeRepository
                .findAll()
                .stream()
                .map(sword -> modelMapper.map(sword,SwordShopView.class))
                .collect(Collectors.toList());
    }

    @Override
    public void buySword(Long id) {
        //Note: The method is turned into commented because a problem arises when trying to
        //remove a record from "swords" table in the database. Fix later.
        SwordForge swordForge = swordForgeRepository.findById(id).orElse(null);
        Long oldId = extraUserData.getSword().getId();
        swordForgeRepository.deleteById(id);
        extraUserData.setSword(swordForge.getSword());
        //Note: Can turn negative. Fix later. Maybe try Binding Result.
        extraUserData.setCoins(extraUserData.getCoins()-swordForge.getPrice());
        userService.saveUserData();
        swordService.discard(oldId);
    }

    @Override
    public void buyRandom() {
        extraUserData.setCoins(extraUserData.getCoins()-200);
    }


}
